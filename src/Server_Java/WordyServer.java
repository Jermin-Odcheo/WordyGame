package Server_Java;

import Client_Java.myConnection;
import Server_Java.corba.*;
import org.omg.CORBA.ORB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class WordyServer extends wordyPOA {
    private static final List<String> players = new ArrayList<String>();
    private final List<String> rounds = new ArrayList<>();
    private static final List<String> lobbyPlayers = new ArrayList<>();
    private final ArrayList<String> playersInGame = new ArrayList<String>();
    private static final Map<String, String> playerLettersMap = new HashMap<>();
    Map<String, WordyCallback> playerCallbacks = new HashMap<>();
    private int countdown = 10;
    private boolean isGameStarted;

    private String letters;
    private WordyCallback callback;


    public String login(String username, String password) {
        if (players.contains(username)){
            System.out.println("Already Logged In!");
            return "LoggedIn";
        }
        try {
            boolean check = verifyUsername(username);
            if (!check) {
                System.out.println(username + " : " + "Account not found");
                return "NotFound";
            } else {
                PreparedStatement preparedStatement;
                ResultSet resultSet;
                preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM users WHERE user_username = ? AND user_password = ?");
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                        players.add(username);
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
    @Override
    public void exit(String username) {
        players.remove(username);
        playersInGame.remove(username);
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
    private boolean countdownStarted;
    public boolean joinLobby(String playerName) throws GameException {
        if (lobbyPlayers.size() >= 5) {
            System.out.println("Lobby is Full");
            return false;
        }
        lobbyPlayers.add(playerName);
        System.out.println("Player " + playerName + " joined the lobby.");
        if (lobbyPlayers.size() >=2 && !countdownStarted) {
            timerServer();
            countdownStarted = true;
        }
        return true;
    }
    public void timerServer(){
        new Thread(() -> {
            while (countdown > 0) {
                System.out.println("Countdown: " + countdown);
                countdown--;
                try {
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (lobbyPlayers.size() >= 2) {
                startGame();
            } else {
                countdownStarted = false;
            }

        }).start();
    }
    public double gettimer() {

        return countdown;
    }

    public String generateLetters(String letters) {
        return null;
    }

    @Override
    public String playerInGameList() {
        return lobbyPlayers.toString();
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
            WordyCallback wordyCallback = playerCallbacks.get(lobbyPlayers);
            // TODO: Send generated letters to player
        }
        lobbyPlayers.clear();
        countdown = 10;
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
        for (String p: lobbyPlayers){
            playerLettersMap.put(p,letters);
        }
        System.out.println("Starting new round with letters: " + letters);
        //callback.sendGeneratedLetter(letters);
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

    public void unregisterCallback(String playerName) {
        playerCallbacks.remove(playerName);
    }
    public void leaveGame(String playerName) throws GameException {
        if (playersInGame.contains(playerName) || lobbyPlayers.contains(playerName)) {
            playersInGame.remove(playerName);
            lobbyPlayers.remove(playerName);
            if (playersInGame.size() == 0 || lobbyPlayers.size() == 0) {
                isGameStarted = false;
            }
        } else {
            throw new GameException("Player " + playerName + " is not in the game");
        };
    }


    public void leaveLobby(String playerName) throws GameException {
        if (lobbyPlayers.contains(playerName)) {
            lobbyPlayers.remove(playerName);
            System.out.println("Player " + playerName + " has left the lobby.");
        } else {
            throw new GameException("Player " + playerName + " is not in the lobby");
        }
    }
    private Timer timer;
    //METHOD TIMER TO START THE LOBBY



    @Override
    public double lobbyPlayerCount() {
        return lobbyPlayers.size();
    }
        public static class WordyCallback extends WordyCallbackPOA {
            private final List<WordyCallback> callbacks = new ArrayList<>();
            private ORB orb;

            public void setORB(ORB orb_val) {
                orb = orb_val;
            }

            @Override
            public String sendGeneratedLetter(String letters) {
                return letters;
            }
        }


}
