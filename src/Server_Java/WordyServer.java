package Server_Java;

import Client_Java.myConnection;
import Server_Java.corbaGame.*;

import java.sql.*;
import java.util.*;

public class WordyServer extends wordyPOA {
    private static final List<String> players = new ArrayList<>();
    private static final List<String> lobbyPlayers = new ArrayList<>();
    private final List<String> rounds = new ArrayList<>();
    private final List<String> playersInGame = new ArrayList<>();
    private final List<String> playersWaitingForRestart = new ArrayList<>();

    private final Map<String, Integer> clientWinCount = new HashMap<>();
    private final Map<String, String> clientWords = new HashMap<>();
    private final Map<String, String> lastGameWords = new HashMap<>();
    private String lastGameWinner = "";
    private long lastGameEndTime = 0;
    private final List<String> words = new ArrayList<>();

    private int countdown = 10;
    private int gameTimer = 30;  // Game duration
    private boolean isGameStarted = false;
    private boolean isGameEnded = false;
    private boolean countdownStarted = false;
    private String letters;
    private String currentWinner = "";

    private Timer gameTimerInstance;

    @Override
    public void login(String username, String password) throws checkLogin, notFound, invalid, validatedLogin {
        boolean exists = verifyUsername(username);
        if (!exists) {
            System.out.println(username + " : Account not found");
            throw new notFound(username + " : Account not found");
        }
        try {
            PreparedStatement ps = myConnection.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_username = ? AND user_password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (players.contains(username)) {
                    throw new checkLogin("Already Logged In!");
                }
                players.add(username);
                throw new validatedLogin(username + " : Successfully Logged In");
            } else {
                throw new invalid("Invalid Credentials!");
            }
        } catch (SQLException e) {
            System.err.println("Server Side Error: " + e.getMessage());
        }
    }

    @Override
    public void exit(String username) {
        players.remove(username);
        lobbyPlayers.remove(username);
        playersInGame.remove(username);
        playersWaitingForRestart.remove(username);
        clientWords.remove(username);
        System.out.println(username + ": Logged Out!");
    }

    @Override
    public boolean status(String playerName) {
        return playersInGame.contains(playerName);
    }

    private boolean verifyUsername(String username) {
        try {
            PreparedStatement ps = myConnection.getConnection().prepareStatement(
                    "SELECT user_username FROM users WHERE user_username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean joinLobby(String playerName) {
        synchronized (this) {
            // Clean up if rejoining
            if (lobbyPlayers.contains(playerName) || playersInGame.contains(playerName)) {
                try {
                    leaveGame(playerName);
                } catch (GameException e) {
                    System.err.println("Error leaving game: " + e.getMessage());
                }
            }
            // Reset if previous game ended fully
            if (isGameEnded && playersInGame.isEmpty()) {
                resetGameState();
            }
            // Reject if game in progress
            if (isGameStarted && !isGameEnded) {
                return false;
            }
            // Limit lobby size
            if (lobbyPlayers.size() >= 5) {
                return false;
            }
            // Add to lobby
            if (!lobbyPlayers.contains(playerName)) {
                lobbyPlayers.add(playerName);
                System.out.println("Lobby: " + lobbyPlayers);
            }
            // Start countdown when enough players
            if (lobbyPlayers.size() >= 2 && !countdownStarted) {
                countdownStarted = true;
                timerServer();
            }
            return true;
        }
    }

    private void resetGameState() {
        System.out.println("Resetting game state...");
        isGameStarted = false;
        isGameEnded = false;
        countdown = 10;
        gameTimer = 30;
        countdownStarted = false;
        currentWinner = "";

        playersInGame.clear();
        clientWords.clear();
        words.clear();
        playersWaitingForRestart.clear();

        if (gameTimerInstance != null) {
            gameTimerInstance.cancel();
            gameTimerInstance = null;
        }
        letters = null;
        System.out.println("Game state reset completed");
    }

    @Override
    public void timerServer() {
        new Thread(() -> {
            try {
                while (countdown > 0 && lobbyPlayers.size() >= 2 && !isGameStarted) {
                    System.out.println("Lobby Countdown: " + countdown);
                    countdown--;
                    Thread.sleep(1000);
                    if (lobbyPlayers.size() < 2) {
                        System.out.println("Not enough players, resetting countdown");
                        countdown = 10;
                        countdownStarted = false;
                        return;
                    }
                }
                if (lobbyPlayers.size() >= 2 && countdown <= 0 && !isGameStarted) {
                    startGame();
                } else {
                    System.out.println("Countdown aborted or insufficient players");
                    countdown = 10;
                    countdownStarted = false;
                }
            } catch (InterruptedException e) {
                System.out.println("Timer interrupted");
                countdown = 10;
                countdownStarted = false;
            }
        }).start();
    }

    @Override
    public double gettimer() {
        if (isGameStarted && !isGameEnded) return gameTimer;
        if (countdownStarted && countdown > 0) return countdown;
        return 0;
    }

    public int getLobbyTimer() {
        return Math.max(0, countdown);
    }

    public int getGameTimerValue() {
        return Math.max(0, gameTimer);
    }

    @Override
    public String getGeneratedLetter() {
        return letters;
    }

    @Override
    public String playerInGameList() {
        return players.toString();
    }

    @Override
    public void generateLetters() {
        generateRandomLetters();
    }

    private void startGame() {
        if (lobbyPlayers.size() < 2) {
            countdown = 10;
            countdownStarted = false;
            return;
        }
        isGameStarted = true;
        isGameEnded = false;
        playersInGame.clear();
        playersInGame.addAll(lobbyPlayers);
        clientWords.clear();
        words.clear();
        currentWinner = "";
        gameTimer = 30;

        System.out.println("Game starting with: " + playersInGame);
        generateRandomLetters();
        System.out.println("Letters: " + letters);

        startGameTimer();
        lobbyPlayers.clear();
        countdown = 10;
        countdownStarted = false;
    }

    private void startGameTimer() {
        gameTimerInstance = new Timer();
        gameTimerInstance.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameTimer--;
                if (gameTimer <= 0) {
                    endGame();
                    gameTimerInstance.cancel();
                }
            }
        }, 1000, 1000);
    }

    private void endGame() {
        isGameEnded = true;
        isGameStarted = false;

        lastGameWords.clear();
        lastGameWords.putAll(clientWords);
        lastGameEndTime = System.currentTimeMillis();

        determineAndStoreWinner();
        lastGameWinner = currentWinner;

        System.out.println("Game ended: " + clientWords);

        Timer cleanupTimer = new Timer();
        cleanupTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (WordyServer.this) {
                    if (playersWaitingForRestart.size() >= 2) {
                        System.out.println("Restarting game for players: " + playersWaitingForRestart);
                        startGame();
                    } else {
                        System.out.println("Cleaning up for next session");
                        resetGameState();
                    }
                }
                cleanupTimer.cancel();
            }
        }, 30000);
    }

    private void determineAndStoreWinner() {
        if (clientWords.isEmpty()) {
            currentWinner = "NO_SUBMISSIONS";
            return;
        }
        int maxLen = clientWords.values().stream().mapToInt(String::length).max().orElse(0);
        List<String> winners = new ArrayList<>();
        clientWords.forEach((p,w) -> { if (w.length() == maxLen) winners.add(p); });

        if (winners.size() == 1) {
            currentWinner = "WINNER:" + winners.get(0);
            addOrUpdateUser(winners.get(0));
        } else {
            currentWinner = "TIE:" + String.join(",", winners) + ":" + maxLen;
        }
    }

    @Override
    public void playWord(String playerName, String word) throws InvalidWord {
        String w = word.toUpperCase();
        synchronized (this) {
            if (!isGameStarted || isGameEnded) {
                throw new InvalidWord("Game is not active");
            }
            if (words.contains(w)) {
                throw new InvalidWord("Word already submitted");
            }
            if (!canFormWord(w)) {
                throw new InvalidWord("Word cannot be formed from letters");
            }
            if (!isValidWord(w)) {
                throw new InvalidWord("Word not valid");
            }
            words.add(w);
            clientWords.put(playerName, w);
            System.out.println("Submitted: " + playerName + " -> " + w);
        }
    }

    @Override
    public void votePlayAgain(String playerName) {
        synchronized (this) {
            if (!playersWaitingForRestart.contains(playerName)) {
                playersWaitingForRestart.add(playerName);
                System.out.println(playerName + " voted to play again");
            }
        }
    }

    @Override
    public String[] getPlayAgainList() {
        synchronized (this) {
            return playersWaitingForRestart.toArray(new String[0]);
        }
    }

    @Override
    public double lobbyPlayerCount() {
        return lobbyPlayers.size();
    }

    @Override
    public void leaveGame(String playerName) throws GameException {
        synchronized (this) {
            lobbyPlayers.remove(playerName);
            playersInGame.remove(playerName);
            playersWaitingForRestart.remove(playerName);
            clientWords.remove(playerName);
            if (lobbyPlayers.isEmpty()) {
                countdown = 10;
                countdownStarted = false;
            }
            if (isGameStarted && playersInGame.isEmpty()) {
                endGame();
            }
        }
    }

    @Override
    public String[] displayWordList() {
        return words.toArray(new String[0]);
    }

    @Override
    public String[] displayWinsList() {
        return currentWinner != null && !currentWinner.isEmpty()
                ? new String[]{currentWinner} : new String[0];
    }

    @Override
    public String getValidWordFromClients() {
        System.out.println("Fetching submitted words...");
        Map<String,String> source = clientWords.isEmpty() && !lastGameWords.isEmpty()
                && System.currentTimeMillis()-lastGameEndTime <= 60000
                ? lastGameWords : clientWords;
        if (source.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        source.forEach((p,w) -> sb.append(p).append(": ").append(w).append("\n"));
        return sb.toString();
    }

    @Override
    public void getWinner() throws isSameLength, getWin, getRoundWin {
        if (!isGameEnded) throw new isSameLength("Game still in progress");
        String w = currentWinner.isEmpty() && System.currentTimeMillis()-lastGameEndTime<=60000
                ? lastGameWinner : currentWinner;
        if (w.startsWith("WINNER:")) {
            throw new getWin("Winner is: " + w.substring(7));
        } else if (w.startsWith("TIE:")) {
            throw new isSameLength("Tie between players");
        } else {
            throw new isSameLength("No submissions");
        }
    }

    @Override
    public void startNewRound() {
        resetGameState();
        System.out.println("New round initialized");
    }

    @Override
    public String getGameState() {
        if (countdownStarted && countdown>0) return "LOBBY_COUNTDOWN:"+countdown;
        if (isGameStarted && !isGameEnded) return "GAME_ACTIVE:"+gameTimer;
        if (isGameEnded) return "GAME_ENDED:"+currentWinner;
        return "WAITING_FOR_PLAYERS";
    }

    @Override
    public int getServerTimestamp() {
        return (int)System.currentTimeMillis();
    }

    private void generateRandomLetters() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 13; i++) sb.append(abc.charAt(rand.nextInt(abc.length())));
        letters = sb.toString();
    }

    private boolean canFormWord(String word) {
        if (letters==null) return false;
        String avail = letters;
        for (char c: word.toCharArray()) {
            int idx = avail.indexOf(c);
            if (idx<0) return false;
            avail = avail.substring(0,idx) + avail.substring(idx+1);
        }
        return true;
    }

    private boolean isValidWord(String word) {
        // Use JazzyWordValidator for simple word checking
        JazzyWordValidator.ValidationResult result = JazzyWordValidator.validateWordDetailed(word);

        // Log validation result for debugging
        System.out.println("Jazzy validation for '" + word + "': " + result);

        return result.isValid();
    }

    private void addOrUpdateUser(String username) {
        try {
            Connection conn = myConnection.getConnection();
            PreparedStatement chk = conn.prepareStatement("SHOW COLUMNS FROM users LIKE 'win_count'");
            ResultSet col = chk.executeQuery();
            if (!col.next()) {
                conn.prepareStatement("ALTER TABLE users ADD COLUMN win_count INT DEFAULT 0").executeUpdate();
            }
            PreparedStatement upd = conn.prepareStatement(
                    "UPDATE users SET win_count = COALESCE(win_count,0)+1 WHERE user_username = ?");
            upd.setString(1, username);
            upd.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Win update error: " + e.getMessage());
        }
    }

    public static boolean isWordValid(String word) {
        return JazzyWordValidator.isWordValid(word);
    }
}
