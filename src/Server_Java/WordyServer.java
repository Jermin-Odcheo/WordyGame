package Server_Java;

import corbaGame.*;

import java.sql.*;
import java.util.*;

public class WordyServer extends wordyPOA {
    private static final List<String> players = new ArrayList<>();
    private static final List<String> lobbyPlayers = new ArrayList<>();
    // private final List<String> rounds = new ArrayList<>(); // removed unused
    private final List<String> playersInGame = new ArrayList<>();
    private final List<String> playersWaitingForRestart = new ArrayList<>();

    // private final Map<String, Integer> clientWinCount = new HashMap<>(); // removed unused
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
            PreparedStatement ps = MyConnection.getConnection().prepareStatement(
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

    // Not exposed via IDL; internal convenience
    public boolean status(String playerName) {
        return playersInGame.contains(playerName);
    }

    private boolean verifyUsername(String username) {
        try {
            PreparedStatement ps = MyConnection.getConnection().prepareStatement(
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
        lastGameWinner = extractWinnerName(currentWinner);

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

    private String extractWinnerName(String currentWinner) {
        if (currentWinner == null) return "";
        if (currentWinner.startsWith("WINNER:")) {
            return currentWinner.substring(7);
        }
        return "";
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
        Map<String, String> source;
        if (!clientWords.isEmpty()) {
            source = clientWords;
        } else if (!lastGameWords.isEmpty()) {
            source = lastGameWords;
        } else {
            return new String[0];
        }
        List<String> list = new ArrayList<>();
        source.forEach((user, word) -> list.add(user + "," + word));
        return list.toArray(new String[0]);
    }

    @Override
    public String[] displayWinsList() {
        List<String> results = new ArrayList<>();
        try (Connection conn = MyConnection.getConnection()) {
            if (conn == null) return new String[0];
            // Ensure win_count column exists
            try (PreparedStatement chk = conn.prepareStatement("SHOW COLUMNS FROM users LIKE 'win_count'")) {
                ResultSet rs = chk.executeQuery();
                if (!rs.next()) {
                    try (PreparedStatement alter = conn.prepareStatement("ALTER TABLE users ADD COLUMN win_count INT DEFAULT 0")) {
                        alter.executeUpdate();
                    }
                }
            }
            String sql = "SELECT user_username, COALESCE(win_count,0) AS wins FROM users ORDER BY wins DESC, user_username ASC LIMIT 10";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String user = rs.getString("user_username");
                    int wins = rs.getInt("wins");
                    results.add(user + "," + wins);
                }
            }
        } catch (SQLException e) {
            System.err.println("displayWinsList error: " + e.getMessage());
        }
        return results.toArray(new String[0]);
    }

    @Override
    public void getWinner() throws isSameLength, getWin, getRoundWin {
        if (!isGameEnded) throw new isSameLength("Game still in progress");
        String w = currentWinner.isEmpty() && System.currentTimeMillis()-lastGameEndTime<=60000
                ? (lastGameWinner.isEmpty()? currentWinner : "WINNER:"+lastGameWinner)
                : currentWinner;
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
    public int getCurrentGameTime() {
        return getGameTimerValue();
    }

    @Override
    public int getLobbyCountdown() {
        return getLobbyTimer();
    }

    @Override
    public boolean isInLobbyCountdown() {
        return countdownStarted && countdown > 0;
    }

    @Override
    public int getServerTimestamp() {
        return (int)System.currentTimeMillis();
    }

    @Override
    public boolean isGameActive() {
        return isGameStarted && !isGameEnded;
    }

    @Override
    public boolean getGameEnded() {
        return isGameEnded;
    }

    @Override
    public String getWinnerName() {
        if (isGameEnded) {
            if (currentWinner.startsWith("WINNER:")) return currentWinner.substring(7);
            if (!lastGameWinner.isEmpty()) return lastGameWinner;
        }
        return "";
    }

    // ---------------------------------------------------------------------
    // New: Provide newline-delimited list of "player: word" for client UIs
    // Prefers current game's submissions; falls back to last game's results.
    @Override
    public String getValidWordFromClients() {
        synchronized (this) {
            Map<String, String> source = null;
            if (!clientWords.isEmpty()) {
                source = clientWords;
            } else if (!lastGameWords.isEmpty()) {
                source = lastGameWords;
            }
            if (source == null || source.isEmpty()) return "";

            // Sort by player name for deterministic output
            List<Map.Entry<String, String>> entries = new ArrayList<>(source.entrySet());
            entries.sort(Comparator.comparing(Map.Entry::getKey, String.CASE_INSENSITIVE_ORDER));

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> e : entries) {
                sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            }
            return sb.toString().trim();
        }
    }
    // ---------------------------------------------------------------------

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
            Connection conn = MyConnection.getConnection();
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
}
