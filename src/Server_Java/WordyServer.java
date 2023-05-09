package Server_Java;

import Client_Java.myConnection;
import Server_Java.corba.GameException;
import Server_Java.corba.WordyCallback;
import Server_Java.corba.wordyPOA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class WordyServer extends wordyPOA {
//    private static final List<Player> players = new ArrayList<>();
    private final List<String> rounds = new ArrayList<>();
    private static final List<String> lobbyPlayers = new ArrayList<>();
    private final ArrayList<String> playersInGame = new ArrayList<String>();
    private int countdown = 10;
    private boolean isGameStarted;
    private String letters;
    private WordyCallback callback;
    public String login(String username, String password) {
        try {
            boolean check = verifyUsername(username);
            if (!check) {
                System.out.println(username + " : " + "Account not found");
                return "NotFound";
            } else {
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM `users` WHERE user_username = ? AND user_password = ?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println(username + " : " + "Successfully Logged In");
                        return "Found";
                } else {
                    return "Invalid";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error";
        }

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

    public static class Player {
        private final String name;
        public Player(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
    private void notifyPlayersList(String s) {
        if (callback != null) {
            callback.notifyPlayersList(lobbyPlayers);
        }
    }
    //Player join the lobby
    public boolean joinGame(String playerName) throws GameException {
        if (lobbyPlayers.contains(playerName)) {
            throw new GameException("Player " + playerName + " is already in the lobby.");
        }

        if (lobbyPlayers.size() >= 5) {
            throw new GameException("Lobby is full. Please try again later.");
        }

        lobbyPlayers.add(playerName);
        System.out.println("Player " + playerName + " joined the lobby.");
        notifyPlayersList("Player " + playerName + " joined the lobby.");
        // Start countdown if there are at least 2 players
        if (lobbyPlayers.size() >= 2) {
            new Thread(() -> {
                while (countdown > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countdown--;
                    System.out.println(countdown + " seconds left to join the game!");
                }
                startGame();
            }).start();
        }

        return true;
    }

    // Play Word method
    public void playWord(String playerName, String word) throws GameException {
        // check if game has started
        if (!isGameStarted) {
            throw new GameException("Game has not yet started.");
        }
        // check if player is in game
        if (!playersInGame.contains(playerName)) {
            throw new GameException("Player is not in game.");
        }
        // check if word can be formed from letters
        for (char c : word.toCharArray()) {
            if (letters.indexOf(c) == -1) {
                throw new GameException("Word cannot be formed from letters.");
            }
        }
        System.out.println(playerName + " played the word: " + word);
    }

    private boolean isWordValidForRound(String word) {
        if (rounds.size() == 0) {
            return true;
        }
        String lastWord = rounds.get(rounds.size()-1);
        char lastChar = lastWord.charAt(lastWord.length() - 1);
        char firstChar = word.charAt(0);
        if (lastChar == firstChar) {
            return true;
        } else {
            return false;
        }
    }

    private void startNewRound() {
        String letters = generateRandomLetters();
        rounds.add(letters);
        System.out.println("Starting new round with letters: " + letters);
    }

    private String generateRandomLetters() {
        List<Character> letters = new ArrayList<Character>();
        Random random = new Random();
        int numVowels = random.nextInt(3) + 5; // 5 to 7 vowels
        for (int i = 0; i < numVowels; i++) {
            letters.add(getRandomVowel());
        }
        for (int i = numVowels; i < 17; i++) {
            letters.add(getRandomConsonant());
        }
        Collections.shuffle(letters);
        StringBuilder sb = new StringBuilder();
        for (char c : letters) {
            sb.append(c);
        }
        return sb.toString();
    }


    public String generateLetters() {
        String letters = generateRandomLetters();
        System.out.println("Starting new round with letters: " + letters);
        return letters;
    }

    private char getRandomVowel() {
        String vowels = "AEIOU";
        return vowels.charAt(new Random().nextInt(vowels.length()));
    }

    private char getRandomConsonant() {
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        return consonants.charAt(new Random().nextInt(consonants.length()));
    }
    private Set<String> words;
    // implementation to check if word is valid using .txt file
    private boolean isValidWord(String word) {
        try {
            words = new HashSet<>();
            BufferedReader reader = new BufferedReader(new FileReader("src/Server_Java/words.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toUpperCase());
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Failed to load dictionary: " + e.getMessage());
        }

        return words.contains(word.toUpperCase());
    }

    private void startGame() {
        if (lobbyPlayers.size() < 2) {
            System.out.println("Cannot start game. Not enough players.");
            lobbyPlayers.clear();
            countdown = 10;
            return;
        }

        System.out.println("Starting game with players: " + lobbyPlayers.toString());

        // TODO: Generate 17 random letters with 5-7 vowels
        String letters = generateLetters();

        for (String player : lobbyPlayers) {

            // TODO: Send generated letters to player
        }

        lobbyPlayers.clear();
        countdown = 10;
    }
    public void leaveGame(String playerName) throws GameException {
        if (playersInGame.contains(playerName)) {
            playersInGame.remove(playerName);
            if (playersInGame.size() == 0) {
                isGameStarted = false;
            }
        } else {
            throw new GameException("Player " + playerName + " is not in the game");
        }
        notifyPlayersList("Player " + playerName + " joined the lobby.");
    }

    public void leaveLobby(String playerName) throws GameException {
        if (lobbyPlayers.contains(playerName)) {
            lobbyPlayers.remove(playerName);
            System.out.println("Player " + playerName + " has left the lobby.");
        } else {
            throw new GameException("Player " + playerName + " is not in the lobby");
        }
        notifyPlayersList("Player " + playerName + " joined the lobby.");
    }

    //METHOD TIMER TO START THE LOBBY
    private static Timer timer;
      public boolean timer(){
        WordyCallbackImpl callback = new WordyCallbackImpl();
        int i = 10;
        while (i>=0){
            System.out.println("Remaining: "+i+" seconds");
            if ( i == 0){
                timer.cancel();
                callback.notifyCountdownStarted(i);
                callback.notifyGameStarted();
                if (lobbyPlayers.size() < 2) {
                    System.out.println("NOT ENOUGH PLAYERS");
                    return false;
                } else {
                    System.out.println("STARTING");
                }
            }
            try {
                i--;
                Thread.sleep(1000L);    // 1000L = 1000ms = 1 second
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



}
