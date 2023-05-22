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
    private static final List<String> players = new ArrayList<>();
    private final List<String> rounds = new ArrayList<>();
    private static final List<String> lobbyPlayers = new ArrayList<>();
    private final ArrayList<String> playersInGame = new ArrayList<>();
    private final Map<String, String> clientWords = new HashMap<>();
    private final Map<String, Integer> clientWinCount = new HashMap<>();
    private int countdown = 10;
    private boolean isGameStarted;
    private String letters;


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
    public boolean joinLobby(String playerName) {
        if (lobbyPlayers.size() >= 5) {
            System.out.println("Lobby is Full");
            return false;
        }
        if (!lobbyPlayers.contains(playerName)) {
            lobbyPlayers.add(playerName);
            System.out.println("Player " + playerName + " joined the lobby.");
        }
        if (lobbyPlayers.size() >=2 && !countdownStarted) {
            timerServer();
            countdownStarted = true;
        }
        return true;
    }
    private Timer timer;
    //METHOD TIMER TO START THE LOBBY
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
    public String getGeneratedLetter(){
        return letters;
    }

    @Override
    public String playerInGameList() {
        return lobbyPlayers.toString();
    }

    @Override
    public void generateLetters() {

    }



    private void startGame() {
        if (lobbyPlayers.size() < 2) {
            System.out.println("Cannot start game. Not enough players.");
            lobbyPlayers.clear();
            countdown = 10;
            return;
        }
        System.out.println("Starting game with players: " + lobbyPlayers.toString());
        //Generate 17 random letters with 5-7 vowels
        generateRandomLetters();
        System.out.println("GAME STARTING WITH: " + letters);

        countdown = 10;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lobbyPlayers.clear();
    }

    // Play Word method
    public void playWord(String playerName, String word) throws InvalidWord {

        if (!canFormWord(word)) {
            System.out.println("Cant be formed");
                throw new InvalidWord("Word Cannot Be Formed!");
        }
        if (!isValidWord(word)) {
            System.out.println("Not Valid");
                throw new InvalidWord("Word Not Valid");
        }
        words.add(word);
        clientWords.put(playerName,word);
        System.out.println("Client " + playerName + " submitted word: " + word);
    }

    public String findLongestWord() {
        String longestWord = "";
        for (String word : clientWords.values()) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        return longestWord;
    }
    public String getWinner() {
        String longestWord = findLongestWord();
        String winner = "";
        for (Map.Entry<String, String> entry : clientWords.entrySet()) {
            if (entry.getValue().equals(longestWord)) {
                winner = entry.getKey();
                break;
            }
        }
        return winner + " won with word: " + longestWord;
    }
    @Override
    public String getValidWordFromClients() {
        return String.valueOf(clientWords);
    }

    private boolean canFormWord(String word){
        word = word.toLowerCase();
        letters = letters.toLowerCase();
        Map<Character, Integer> letterCount = new HashMap<>();
        for (char c : letters.toCharArray()) {
            letterCount.put(c, letterCount.getOrDefault(c, 0) + 1);
        }
        for (char c : word.toCharArray()) {
            if (!letterCount.containsKey(c) || letterCount.get(c) == 0) {
                return false;
            }
            letterCount.put(c, letterCount.get(c) - 1);
        }
        return true;
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

    public void startNewRound() {
        letters = generateRandomLetters();
        rounds.add(letters);
        System.out.println("Starting new round with letters: " + letters);
    }

    private String generateRandomLetters() {
        List<Character> letter = new ArrayList<Character>();
        Random random = new Random();
        int numVowels = random.nextInt(3) + 5; // 5 to 7 vowels
        for (int i = 0; i < numVowels; i++) {
            letter.add(getRandomVowel());
        }
        for (int i = numVowels; i < 17; i++) {
            letter.add(getRandomConsonant());
        }
        Collections.shuffle(letter);
        StringBuilder sb = new StringBuilder();
        for (char c : letter) {
            sb.append(c);
        }

        return  letters = sb.toString();
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
            public void sendGeneratedLetter(String letters) {
                System.out.println("Received generated letters: " + letters);
            }
        }
}
