package Server_Java;

import Client_Java.myConnection;
import Server_Java.corbaGame.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class WordyServer extends wordyPOA {
    private static final List<String> players = new ArrayList<>();
    private final List<String> rounds = new ArrayList<>();
    private static final List<String> lobbyPlayers = new ArrayList<>();
    private final ArrayList<String> playersInGame = new ArrayList<>();
    private final ArrayList<String> playersWaitingForRestart = new ArrayList<>();

    private final Map<String, Integer> clientWinCount = new HashMap<>();
    private final Map<String, String> clientWords = new HashMap<>();
    private final List<String> words = new ArrayList<>();
    private int countdown = 10;
    private int gameTimer = 30; // Game duration in seconds
    private boolean isGameStarted = false;
    private boolean isGameEnded = false;
    private String letters;
    private String currentWinner = "";
    private boolean countdownStarted = false;
    private Timer gameTimerInstance;

    public void login(String username, String password) throws checkLogin, notFound, invalid, validatedLogin {
        boolean check = verifyUsername(username);
        if (!check) {
            System.out.println(username + " : " + "Account not found");
            throw new notFound(username + " : " + "Account not found");
        } else {
            try {
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM users WHERE user_username = ? AND user_password = ?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (players.contains(username)) {
                        System.out.println("Already Logged In!");
                        throw new checkLogin("Already Logged In!");
                    } else {
                        players.add(username);
                        System.out.println(username + " : " + "Successfully Logged In");
                        throw new validatedLogin(username + " : " + "Successfully Logged In");
                    }
                } else {
                    throw new invalid("Invalid Credentials!");
                }
            } catch (SQLException e) {
                System.out.println("Server Side Error");
            }
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

    public boolean status(String playerName) {
        return playersInGame.contains(playerName);
    }

    public boolean verifyUsername(String username) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement;
            preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM `users` WHERE user_username =?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("user_username");
                if (username.equals(user)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    //Player join the lobby
    public boolean joinLobby(String playerName) {
        if (isGameStarted) {
            System.out.println("Cannot join - game in progress");
            return false;
        }
        if (lobbyPlayers.size() >= 5) {
            System.out.println("Lobby is Full");
            return false;
        }
        if (!lobbyPlayers.contains(playerName)) {
            lobbyPlayers.add(playerName);
            System.out.println("Player " + playerName + " joined the lobby.");
        }
        if (lobbyPlayers.size() >= 1 && !countdownStarted) {
            timerServer();
            countdownStarted = true;
        }
        return true;
    }

    public void timerServer() {
        new Thread(() -> {
            while (countdown > 0 && lobbyPlayers.size() >= 1) {
                System.out.println("Lobby Countdown: " + countdown);
                countdown--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (lobbyPlayers.size() >= 2) {
                startGame();
            } else {
                countdown = 10;
                countdownStarted = false;
                System.out.println("Not enough players, countdown reset");
            }
        }).start();
    }

    public double gettimer() {
        return countdown;
    }

    public String getGeneratedLetter() {
        return letters;
    }

    @Override
    public String playerInGameList() {
        // Fix: Return appropriate list based on game state
        if (isGameStarted && !playersInGame.isEmpty()) {
            return playersInGame.toString();
        } else {
            return lobbyPlayers.toString();
        }
    }

    @Override
    public void generateLetters() {
        generateRandomLetters();
    }

    private void startGame() {
        if (lobbyPlayers.size() < 2) {
            System.out.println("Cannot start game. Not enough players.");
            countdown = 10;
            countdownStarted = false;
            return;
        }

        // Initialize game state
        isGameStarted = true;
        isGameEnded = false;
        playersInGame.clear();
        playersInGame.addAll(lobbyPlayers);
        clientWords.clear();
        words.clear();
        currentWinner = "";
        gameTimer = 30;

        System.out.println("Starting game with players: " + playersInGame.toString());
        generateRandomLetters();
        System.out.println("GAME STARTING WITH: " + letters);

        // Start game timer
        startGameTimer();

        // Reset lobby
        countdown = 10;
        countdownStarted = false;
        lobbyPlayers.clear();
    }

    private void startGameTimer() {
        gameTimerInstance = new Timer();
        gameTimerInstance.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                gameTimer--;
                System.out.println("Game Timer: " + gameTimer);

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

        // Determine winner
        currentWinner = findLongestWord();
        if (!currentWinner.isEmpty()) {
            addOrUpdateUser(currentWinner);
            System.out.println("Game ended! Winner: " + currentWinner);
        } else {
            System.out.println("Game ended! No winner (no words submitted)");
            currentWinner = "No Winner";
        }

        // Reset for next game
        playersWaitingForRestart.clear();
    }

    public void playWord(String playerName, String word) throws InvalidWord {
        // Fix: Check if game is active before allowing word submission
        if (!isGameStarted || isGameEnded) {
            throw new InvalidWord("Game is not active");
        }

        if (!canFormWord(word)) {
            System.out.println("Can't be formed with available letters");
            throw new InvalidWord("Word Cannot Be Formed!");
        }
        if (!isValidWord(word)) {
            System.out.println("Not a valid word");
            throw new InvalidWord("Word Not Valid");
        }

        words.add(word);
        clientWords.put(playerName, word);
        storeClientWord(playerName, word);
        System.out.println("Client " + playerName + " submitted word: " + word);
    }

    // New methods for game state management
    public boolean isGameActive() {
        return isGameStarted && !isGameEnded;
    }

    public boolean getGameEnded() {
        return isGameEnded;
    }

    public String getWinnerName() {
        return currentWinner;
    }

    public int getGameTimer() {
        return gameTimer;
    }

    public String findLongestWord() {
        String longestWord = "";
        String winnerName = "";

        for (Map.Entry<String, String> entry : clientWords.entrySet()) {
            String playerName = entry.getKey();
            String word = entry.getValue();

            if (word.length() > longestWord.length()) {
                longestWord = word;
                winnerName = playerName;
            }
        }

        return winnerName;
    }

    private void generateRandomLetters() {
        String vowels = "AEIOU";
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        // Add 5-7 vowels
        int vowelCount = 5 + random.nextInt(3);
        for (int i = 0; i < vowelCount; i++) {
            result.append(vowels.charAt(random.nextInt(vowels.length())));
        }

        // Add consonants to make 17 total letters
        int consonantCount = 17 - vowelCount;
        for (int i = 0; i < consonantCount; i++) {
            result.append(consonants.charAt(random.nextInt(consonants.length())));
        }

        // Shuffle the letters
        List<Character> charList = new ArrayList<>();
        for (char c : result.toString().toCharArray()) {
            charList.add(c);
        }
        Collections.shuffle(charList);

        StringBuilder shuffled = new StringBuilder();
        for (char c : charList) {
            shuffled.append(c);
        }

        letters = shuffled.toString();
    }

    private boolean canFormWord(String word) {
        if (letters == null || letters.isEmpty()) {
            return false;
        }

        Map<Character, Integer> letterCount = new HashMap<>();
        for (char c : letters.toCharArray()) {
            letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
        }

        for (char c : word.toUpperCase().toCharArray()) {
            if (!letterCount.containsKey(c) || letterCount.get(c) <= 0) {
                return false;
            }
            letterCount.put(c, letterCount.get(c) - 1);
        }

        return true;
    }

    private boolean isValidWord(String word) {
        if (word.length() < 3) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/Server_Java/words.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addOrUpdateUser(String username) {
        String selectSql = "SELECT * FROM wincount WHERE username = ?";
        String insertSql = "INSERT INTO wincount (username, wins) VALUES (?, 1)";
        String updateSql = "UPDATE wincount SET wins = wins + 1 WHERE username = ?";

        try {
            PreparedStatement selectStatement = myConnection.getConnection().prepareStatement(selectSql);
            PreparedStatement insertStatement = myConnection.getConnection().prepareStatement(insertSql);
            PreparedStatement updateStatement = myConnection.getConnection().prepareStatement(updateSql);

            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setString(1, username);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Win count incremented for user: " + username);
                } else {
                    System.out.println("Failed to increment win count for user: " + username);
                }
            } else {
                insertStatement.setString(1, username);
                int rowsAffected = insertStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User added to wincount table: " + username);
                } else {
                    System.out.println("Failed to add user to wincount table: " + username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void storeClientWord(String username, String word) {
        try {
            String userId = getUserIdByUsername(username);
            String sql = "INSERT INTO wordlist (user_id, word) VALUES (?, ?)";
            PreparedStatement statement;
            statement = myConnection.getConnection().prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, word);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getUserIdByUsername(String username) throws SQLException {
        String userId = null;
        String sql = "SELECT user_id FROM users WHERE user_username = ?";
        PreparedStatement statement;
        statement = myConnection.getConnection().prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            userId = resultSet.getString("user_id");
        }
        resultSet.close();
        statement.close();
        return userId;
    }

    @Override
    public String[] displayWordList() {
        List<String> wordDataList = new ArrayList<>();

        String sql = "SELECT w.user_id, w.word, u.user_username " +
                "FROM wordlist w " +
                "JOIN users u ON w.user_id = u.user_id " +
                "ORDER BY LENGTH(w.word) DESC LIMIT 5";
        try {
            PreparedStatement statement = myConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("user_username");
                String word = resultSet.getString("word");
                String wordData = username + "," + word;
                wordDataList.add(wordData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wordDataList.toArray(new String[0]);
    }

    public String[] displayWinsList() {
        List<String> winsDataList = new ArrayList<>();

        String sql = "SELECT username, wins FROM wincount ORDER BY wins DESC LIMIT 5";
        try {
            PreparedStatement statement = myConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String wins = resultSet.getString("wins");
                String wordData = username + "," + wins;
                winsDataList.add(wordData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return winsDataList.toArray(new String[0]);
    }

    // Missing abstract method implementations from wordyOperations interface
    @Override
    public String getValidWordFromClients() {
        // Return all valid words submitted by clients
        StringBuilder validWords = new StringBuilder();
        for (Map.Entry<String, String> entry : clientWords.entrySet()) {
            validWords.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return validWords.toString();
    }

    @Override
    public void getWinner() throws isSameLength, getWin, getRoundWin {
        // Determine and announce the winner
        String winner = findLongestWord();
        if (winner.isEmpty()) {
            throw new isSameLength("No words submitted or tie game");
        } else {
            System.out.println("Winner: " + winner);
            throw new getWin("Winner is: " + winner);
        }
    }

    @Override
    public void startNewRound() {
        // Reset game state for a new round
        isGameStarted = false;
        isGameEnded = false;
        clientWords.clear();
        words.clear();
        currentWinner = "";
        gameTimer = 30;
        countdown = 10;
        countdownStarted = false;

        // Clear game timer if running
        if (gameTimerInstance != null) {
            gameTimerInstance.cancel();
        }

        System.out.println("New round started - game state reset");
    }

    @Override
    public double lobbyPlayerCount() {
        return lobbyPlayers.size();
    }

    @Override
    public void leaveGame(String playerName) throws GameException {
        try {
            // Remove player from all lists
            lobbyPlayers.remove(playerName);
            playersInGame.remove(playerName);
            playersWaitingForRestart.remove(playerName);
            clientWords.remove(playerName);

            System.out.println("Player " + playerName + " left the game");

            // Check if we need to stop countdown or end game
            if (lobbyPlayers.isEmpty()) {
                countdown = 10;
                countdownStarted = false;
            }

            // If game is in progress and no players left, end game
            if (isGameStarted && playersInGame.isEmpty()) {
                endGame();
            }
        } catch (Exception e) {
            throw new GameException("Error removing player from game: " + e.getMessage());
        }
    }
}
