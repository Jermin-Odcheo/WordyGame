package Server_Java;

import Client_Java.myConnection;
import Server_Java.corba.GameException;
import Server_Java.corba.wordyPOA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class WordyServer extends wordyPOA  {
    private final List<Player> players = new ArrayList<>();
    private int currentPlayer = 0;
    private final List<String> rounds = new ArrayList<>();

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
                    if (!status(username)){
                        return "LoggedIn";
                    }
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
    public boolean status(String username){
        for (Player player : players) {
            if (player.getName().equals(username)) {
                return true;
            }
        }
        players.add(new Player(username));
        return false;
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
                    System.out.println(user + " : " + "Successfully Logged In -Verify");
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

    public boolean joinGame(String playerName) throws GameException {
        if (players.size() >= 4) {
            throw new GameException("Game is full.");
        }
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                throw new GameException("Already Logged In");
            }
        }
        players.add(new Player(playerName));
        System.out.println(playerName + " joined the game.");
        if (players.size() == 4) {
            startGame();
        }
        return true;
    }
    public synchronized void playWord(String playerName, String word) throws GameException {
        if (players.size() < 2) {
            throw new GameException("Not enough players to start the game.");
        }
        if (!isValidWord(word)) {
            throw new GameException("Invalid word.");
        }
        if (!isPlayerTurn(playerName)) {
            throw new GameException("Not your turn.");
        }
        if (!isWordValidForRound(word)) {
            throw new GameException("Word is not valid for this round.");
        }
        System.out.println(playerName + " played the word \"" + word + "\".");
        currentPlayer = (currentPlayer + 1) % players.size();
        if (currentPlayer == 0) {
            startNewRound();
        }
        notifyAll();
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

    private char getRandomVowel() {
        String vowels = "AEIOU";
        return vowels.charAt(new Random().nextInt(vowels.length()));
    }

    private char getRandomConsonant() {
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        return consonants.charAt(new Random().nextInt(consonants.length()));
    }
    private boolean isPlayerTurn(String playerName) {
        return players.get(currentPlayer).getName().equals(playerName);
    }
    private Set<String> words;
    // implementation to check if word is valid using .txt file
    private boolean isValidWord(String word) {
        try {
            words = new HashSet<String>();
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
        startNewRound();
        System.out.println("Game started.");
    }
}
