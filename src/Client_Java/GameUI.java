package Client_Java;

import Client_Java.corbaGame.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static Client_Java.Client.wordyImpl;

public class GameUI extends javax.swing.JFrame {
    private static String username;
    private static JLabel timerField;
    private static int countdownSeconds = 30;
    private Timer gameTimer;
    private Timer gameStateTimer;

    // UI Components
    private JPanel mainPanel;
    private JPanel letterTilesPanel;
    private JPanel wordConstructionPanel;
    private JPanel controlPanel;
    private JPanel playerInfoPanel;

    private JLabel scoreLabel;
    private JTextArea wordHistoryArea;
    private JTextArea playerListArea;
    private JTextField wordInputField;
    private JButton submitWordButton;
    private JButton clearWordButton;
    private JButton shuffleLettersButton;
    private JLabel statusLabel;
    private JLabel wordValueLabel;

    // Game state
    private final List<LetterTile> letterTiles;
    private final List<LetterTile> selectedTiles;
    private final StringBuilder currentWord;
    private int currentScore = 0;

    // Letter values (Scrabble-like scoring)
    private static final Map<Character, Integer> LETTER_VALUES = new HashMap<>();
    static {
        LETTER_VALUES.put('A', 1); LETTER_VALUES.put('E', 1); LETTER_VALUES.put('I', 1);
        LETTER_VALUES.put('O', 1); LETTER_VALUES.put('U', 1); LETTER_VALUES.put('L', 1);
        LETTER_VALUES.put('N', 1); LETTER_VALUES.put('S', 1); LETTER_VALUES.put('T', 1);
        LETTER_VALUES.put('R', 1); LETTER_VALUES.put('D', 2); LETTER_VALUES.put('G', 2);
        LETTER_VALUES.put('B', 3); LETTER_VALUES.put('C', 3); LETTER_VALUES.put('M', 3);
        LETTER_VALUES.put('P', 3); LETTER_VALUES.put('F', 4); LETTER_VALUES.put('H', 4);
        LETTER_VALUES.put('V', 4); LETTER_VALUES.put('W', 4); LETTER_VALUES.put('Y', 4);
        LETTER_VALUES.put('K', 5); LETTER_VALUES.put('J', 8); LETTER_VALUES.put('X', 8);
        LETTER_VALUES.put('Q', 10); LETTER_VALUES.put('Z', 10);
    }

    public GameUI(String username) {
        GameUI.username = username;
        this.letterTiles = new ArrayList<>();
        this.selectedTiles = new ArrayList<>();
        this.currentWord = new StringBuilder();

        initializeUI();
        addListeners();
        startCountdownTimer();
        startGameStateMonitoring(); // Add game state monitoring
    }

    private void initializeUI() {
        setTitle("WordyGame - Modern Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(45, 52, 70));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        createHeaderPanel();
        createGamePanel();

        add(mainPanel);
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(31, 41, 55));
        headerPanel.setBorder(new LineBorder(new Color(75, 85, 99), 2, true));
        headerPanel.setPreferredSize(new Dimension(0, 80));

        // Title and timer
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(31, 41, 55));
        JLabel titleLabel = new JLabel("WordyGame");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Timer and score panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setBackground(new Color(31, 41, 55));

        timerField = new JLabel("30");
        timerField.setFont(new Font("Segoe UI", Font.BOLD, 24));
        timerField.setForeground(new Color(239, 68, 68));
        timerField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(239, 68, 68), 2, true),
            new EmptyBorder(5, 10, 5, 10)
        ));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scoreLabel.setForeground(new Color(34, 197, 94));
        scoreLabel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel currentPlayerLabel = new JLabel("Player: " + username);
        currentPlayerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        currentPlayerLabel.setForeground(new Color(168, 162, 158));

        infoPanel.add(currentPlayerLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(timerField);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createGamePanel() {
        JPanel gamePanel = new JPanel(new BorderLayout(15, 15));
        gamePanel.setBackground(new Color(45, 52, 70));

        // Left side - Game area
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(new Color(45, 52, 70));
        leftPanel.setPreferredSize(new Dimension(750, 0));

        // Word construction area
        createWordConstructionPanel();
        leftPanel.add(wordConstructionPanel, BorderLayout.NORTH);

        // Letter tiles area
        createLetterTilesPanel();
        leftPanel.add(letterTilesPanel, BorderLayout.CENTER);

        // Control buttons
        createGameControlPanel();
        leftPanel.add(controlPanel, BorderLayout.SOUTH);

        // Right side - Player info and game history
        createPlayerInfoPanel();

        gamePanel.add(leftPanel, BorderLayout.CENTER);
        gamePanel.add(playerInfoPanel, BorderLayout.EAST);

        mainPanel.add(gamePanel, BorderLayout.CENTER);
    }

    private void createWordConstructionPanel() {
        wordConstructionPanel = new JPanel(new BorderLayout(10, 10));
        wordConstructionPanel.setBackground(new Color(55, 65, 81));
        wordConstructionPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(107, 114, 128), 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        wordConstructionPanel.setPreferredSize(new Dimension(0, 120));

        JLabel wordLabel = new JLabel("Construct Your Word:");
        wordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        wordLabel.setForeground(Color.WHITE);

        wordInputField = new JTextField();
        wordInputField.setFont(new Font("Segoe UI", Font.BOLD, 24));
        wordInputField.setBackground(new Color(17, 24, 39));
        wordInputField.setForeground(Color.WHITE);
        wordInputField.setCaretColor(Color.WHITE);
        wordInputField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(59, 130, 246), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        wordInputField.setEditable(false); // Words built by clicking tiles

        wordValueLabel = new JLabel("Word Value: 0 points");
        wordValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        wordValueLabel.setForeground(new Color(168, 162, 158));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(55, 65, 81));
        topPanel.add(wordLabel, BorderLayout.WEST);
        topPanel.add(wordValueLabel, BorderLayout.EAST);

        wordConstructionPanel.add(topPanel, BorderLayout.NORTH);
        wordConstructionPanel.add(wordInputField, BorderLayout.CENTER);
    }

    private void createLetterTilesPanel() {
        letterTilesPanel = new JPanel();
        letterTilesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        letterTilesPanel.setBackground(new Color(55, 65, 81));
        letterTilesPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(107, 114, 128), 2, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        letterTilesPanel.setPreferredSize(new Dimension(0, 200));

        JLabel tilesLabel = new JLabel("Your Letter Tiles:");
        tilesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tilesLabel.setForeground(Color.WHITE);

        // This will be populated when letters are received from server
    }

    private void createGameControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(new Color(45, 52, 70));
        controlPanel.setPreferredSize(new Dimension(0, 80));

        submitWordButton = createStyledButton("Submit Word", new Color(34, 197, 94));
        clearWordButton = createStyledButton("Clear", new Color(239, 68, 68));
        shuffleLettersButton = createStyledButton("Shuffle Letters", new Color(59, 130, 246));

        controlPanel.add(submitWordButton);
        controlPanel.add(clearWordButton);
        controlPanel.add(shuffleLettersButton);

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(new Color(251, 191, 36));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(45, 52, 70));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel fullControlPanel = new JPanel(new BorderLayout());
        fullControlPanel.setBackground(new Color(45, 52, 70));
        fullControlPanel.add(controlPanel, BorderLayout.CENTER);
        fullControlPanel.add(statusPanel, BorderLayout.SOUTH);

        controlPanel = fullControlPanel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color.darker(), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void createPlayerInfoPanel() {
        playerInfoPanel = new JPanel(new BorderLayout(10, 10));
        playerInfoPanel.setBackground(new Color(45, 52, 70));
        playerInfoPanel.setPreferredSize(new Dimension(300, 0));

        // Player list
        JPanel playersPanel = new JPanel(new BorderLayout(5, 5));
        playersPanel.setBackground(new Color(55, 65, 81));
        playersPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(107, 114, 128), 2, true),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel playersLabel = new JLabel("Players");
        playersLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playersLabel.setForeground(Color.WHITE);

        playerListArea = new JTextArea();
        playerListArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playerListArea.setBackground(new Color(17, 24, 39));
        playerListArea.setForeground(new Color(209, 213, 219));
        playerListArea.setEditable(false);
        playerListArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane playersScroll = new JScrollPane(playerListArea);
        playersScroll.setPreferredSize(new Dimension(0, 200));
        playersScroll.setBorder(null);

        playersPanel.add(playersLabel, BorderLayout.NORTH);
        playersPanel.add(playersScroll, BorderLayout.CENTER);

        // Word history
        JPanel historyPanel = new JPanel(new BorderLayout(5, 5));
        historyPanel.setBackground(new Color(55, 65, 81));
        historyPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(107, 114, 128), 2, true),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel historyLabel = new JLabel("Word History");
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        historyLabel.setForeground(Color.WHITE);

        wordHistoryArea = new JTextArea();
        wordHistoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        wordHistoryArea.setBackground(new Color(17, 24, 39));
        wordHistoryArea.setForeground(new Color(209, 213, 219));
        wordHistoryArea.setEditable(false);
        wordHistoryArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        wordHistoryArea.setLineWrap(true);
        wordHistoryArea.setWrapStyleWord(true);

        JScrollPane historyScroll = new JScrollPane(wordHistoryArea);
        historyScroll.setBorder(null);

        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(historyScroll, BorderLayout.CENTER);

        playerInfoPanel.add(playersPanel, BorderLayout.NORTH);
        playerInfoPanel.add(historyPanel, BorderLayout.CENTER);
    }

    // Letter tile class for interactive tiles
    private class LetterTile extends JButton {
        private final char letter;
        private final int value;
        private boolean isSelected;

        public LetterTile(char letter) {
            this.letter = letter;
            this.value = LETTER_VALUES.getOrDefault(Character.toUpperCase(letter), 1);
            this.isSelected = false;

            setupTile();
        }

        private void setupTile() {
            setText("<html><div style='text-align: center;'><b>" +
                   Character.toUpperCase(letter) + "</b><br><small>" + value + "</small></div></html>");
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setPreferredSize(new Dimension(60, 60));
            setBackground(new Color(251, 191, 36));
            setForeground(new Color(92, 77, 192));
            setBorder(new LineBorder(new Color(217, 119, 6), 2, true));
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addActionListener(e -> toggleSelection());
        }

        private void toggleSelection() {
            if (isSelected) {
                deselect();
                removeFromWord();
            } else {
                select();
                addToWord();
            }
        }

        private void select() {
            isSelected = true;
            setBackground(new Color(59, 130, 246));
            setForeground(Color.WHITE);
            selectedTiles.add(this);
        }

        private void deselect() {
            isSelected = false;
            setBackground(new Color(251, 191, 36));
            setForeground(new Color(92, 77, 192));
            selectedTiles.remove(this);
        }

        private void addToWord() {
            currentWord.append(Character.toUpperCase(letter));
            updateWordDisplay();
        }

        private void removeFromWord() {
            // Remove last occurrence of this letter
            String word = currentWord.toString();
            int lastIndex = word.lastIndexOf(Character.toUpperCase(letter));
            if (lastIndex >= 0) {
                currentWord.deleteCharAt(lastIndex);
                updateWordDisplay();
            }
        }

        public int getValue() { return value; }
        public boolean isSelected() { return isSelected; }
    }

    private void updateWordDisplay() {
        wordInputField.setText(currentWord.toString());
        int wordValue = calculateWordValue();
        wordValueLabel.setText("Word Value: " + wordValue + " points");
    }

    private int calculateWordValue() {
        int total = 0;
        for (LetterTile tile : selectedTiles) {
            total += tile.getValue();
        }
        return total;
    }

    private void populateLetterTiles(String letters) {
        letterTilesPanel.removeAll();
        letterTiles.clear();

        JLabel tilesLabel = new JLabel("Your Letter Tiles:");
        tilesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tilesLabel.setForeground(Color.WHITE);
        letterTilesPanel.add(tilesLabel);

        for (char c : letters.toCharArray()) {
            if (Character.isLetter(c)) {
                LetterTile tile = new LetterTile(c);
                letterTiles.add(tile);
                letterTilesPanel.add(tile);
            }
        }

        letterTilesPanel.revalidate();
        letterTilesPanel.repaint();
    }

    public void addListeners() {
        // Window listener for game initialization
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    String playerList = wordyImpl.playerInGameList();
                    playerListArea.setText(playerList.replace(",", "\n").substring(1, playerList.length() - 1));

                    String letters = wordyImpl.getGeneratedLetter();
                    populateLetterTiles(letters);

                    statusLabel.setText("Game started! Create words from your letters.");
                } catch (Exception ex) {
                    statusLabel.setText("Error connecting to game server.");
                }
            }

            @Override public void windowClosing(WindowEvent e) {}
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        // Button listeners
        submitWordButton.addActionListener(this::submitWord);
        clearWordButton.addActionListener(e -> clearCurrentWord());
        shuffleLettersButton.addActionListener(e -> shuffleLetters());
    }

    private void startGameStateMonitoring() {
        gameStateTimer = new Timer(2000, e -> { // Check every 2 seconds
            try {
                // Update player list to keep all clients synchronized
                updatePlayerList();

                // Check if game has ended on server side
                checkGameEndStatus();

            } catch (Exception ex) {
                // Silently handle connection errors to avoid spam
            }
        });
        gameStateTimer.start();
    }

    private void updatePlayerList() {
        try {
            String playerList = wordyImpl.playerInGameList();
            if (playerList != null && !playerList.trim().isEmpty() && !playerList.equals("[]")) {
                String formattedList = playerList.replace(",", "\n")
                    .replace("[", "").replace("]", "").trim();
                if (!playerListArea.getText().equals(formattedList)) {
                    SwingUtilities.invokeLater(() -> playerListArea.setText(formattedList));
                }
            }
        } catch (Exception e) {
            // Silently handle - server might be temporarily unavailable
        }
    }

    private void checkGameEndStatus() {
        try {
            // Try to call a server method that would fail if game is over
            // We can use the existing methods to detect game state
            if (countdownSeconds <= 0 && !gameEndedShown) {
                handleGameEnd();
            }
        } catch (Exception e) {
            // Handle any server-side errors
        }
    }

    private boolean gameEndedShown = false;

    private void handleGameEnd() {
        if (gameEndedShown) return; // Prevent multiple calls

        gameEndedShown = true;

        // Stop timers
        if (gameTimer != null) {
            gameTimer.stop();
        }

        // Disable game controls
        submitWordButton.setEnabled(false);
        clearWordButton.setEnabled(false);
        shuffleLettersButton.setEnabled(false);

        // Disable letter tiles
        for (LetterTile tile : letterTiles) {
            tile.setEnabled(false);
        }

        // Show game end dialog
        showGameEndDialog();
    }

    private void showGameEndDialog() {
        SwingUtilities.invokeLater(() -> {
            JDialog gameEndDialog = new JDialog(this, "Game Over!", true);
            gameEndDialog.setSize(450, 300);
            gameEndDialog.setLocationRelativeTo(this);
            gameEndDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

            JPanel dialogPanel = new JPanel(new BorderLayout(15, 15));
            dialogPanel.setBackground(new Color(45, 52, 70));
            dialogPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

            // Winner announcement
            JLabel titleLabel = new JLabel("🎉 Game Over! 🎉", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
            titleLabel.setForeground(new Color(251, 191, 36));

            // Try to get winner from server (you'll need to implement getWinnerName() in server)
            String winnerText = "Time's up! Check the word history to see who had the longest word.";
            try {
                // For now, we'll show a generic message
                // In the future, you can add: String winner = wordyImpl.getWinnerName();
                winnerText = "Game completed! The winner is determined by the longest word submitted.";
            } catch (Exception e) {
                winnerText = "Game completed! Unable to determine winner.";
            }

            JLabel resultLabel = new JLabel("<html><div style='text-align: center;'>" +
                winnerText + "</div></html>", SwingConstants.CENTER);
            resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            resultLabel.setForeground(new Color(209, 213, 219));

            // Score display
            JLabel scoreDisplayLabel = new JLabel("Your final score: " + currentScore + " points", SwingConstants.CENTER);
            scoreDisplayLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            scoreDisplayLabel.setForeground(new Color(34, 197, 94));

            JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
            messagePanel.setBackground(new Color(45, 52, 70));
            messagePanel.add(titleLabel, BorderLayout.NORTH);
            messagePanel.add(resultLabel, BorderLayout.CENTER);
            messagePanel.add(scoreDisplayLabel, BorderLayout.SOUTH);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            buttonPanel.setBackground(new Color(45, 52, 70));

            JButton playAgainButton = createStyledButton("🔄 Play Again", new Color(34, 197, 94));
            JButton backToLobbyButton = createStyledButton("🏠 Back to Lobby", new Color(59, 130, 246));

            playAgainButton.addActionListener(e -> {
                gameEndDialog.dispose();
                requestPlayAgain();
            });

            backToLobbyButton.addActionListener(e -> {
                gameEndDialog.dispose();
                backToLobby();
            });

            buttonPanel.add(playAgainButton);
            buttonPanel.add(backToLobbyButton);

            dialogPanel.add(messagePanel, BorderLayout.CENTER);
            dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

            gameEndDialog.add(dialogPanel);
            gameEndDialog.setVisible(true);
        });
    }

    private void requestPlayAgain() {
        try {
            statusLabel.setText("Requesting to play again...");
            statusLabel.setForeground(new Color(251, 191, 36));

            // Show waiting dialog for other players
            showWaitingForPlayersDialog();

        } catch (Exception e) {
            showStatus("Error requesting play again: " + e.getMessage(), Color.RED);
        }
    }

    private void showWaitingForPlayersDialog() {
        JDialog waitingDialog = new JDialog(this, "Waiting for Players", true);
        waitingDialog.setSize(400, 250);
        waitingDialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel(new BorderLayout(15, 15));
        dialogPanel.setBackground(new Color(45, 52, 70));
        dialogPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel waitingLabel = new JLabel("⏳ Waiting for other players...", SwingConstants.CENTER);
        waitingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        waitingLabel.setForeground(Color.WHITE);

        JLabel statusLabel = new JLabel("Players ready: " + username, SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setForeground(new Color(168, 162, 158));

        JLabel instructionLabel = new JLabel("When all players are ready, a new game will start automatically.", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(new Color(156, 163, 175));

        JPanel messagePanel = new JPanel(new BorderLayout(10, 10));
        messagePanel.setBackground(new Color(45, 52, 70));
        messagePanel.add(waitingLabel, BorderLayout.NORTH);
        messagePanel.add(statusLabel, BorderLayout.CENTER);
        messagePanel.add(instructionLabel, BorderLayout.SOUTH);

        JButton cancelButton = createStyledButton("❌ Cancel", new Color(239, 68, 68));
        cancelButton.addActionListener(e -> {
            waitingDialog.dispose();
            backToLobby();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(45, 52, 70));
        buttonPanel.add(cancelButton);

        dialogPanel.add(messagePanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        waitingDialog.add(dialogPanel);
        waitingDialog.setVisible(true);
    }

    private void backToLobby() {
        try {
            // Clean up timers
            if (gameTimer != null) {
                gameTimer.stop();
            }
            if (gameStateTimer != null) {
                gameStateTimer.stop();
            }

            // Close this window
            this.dispose();

            // Return to lobby (implement this based on your lobby system)
            SwingUtilities.invokeLater(() -> {
                try {
                    // You can implement lobby return here
                    // For example: LobbyUI.startLobby(username);
                    System.out.println("Player " + username + " returned to lobby");

                    // For now, we'll just show a message
                    JOptionPane.showMessageDialog(null,
                        "Returning to lobby...\nRestart the client to join a new game.",
                        "Return to Lobby",
                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    System.out.println("Error returning to lobby: " + e.getMessage());
                }
            });

        } catch (Exception e) {
            showStatus("Error returning to lobby: " + e.getMessage(), Color.RED);
        }
    }

    private void clearCurrentWord() {
        currentWord.setLength(0);
        for (LetterTile tile : selectedTiles) {
            tile.deselect();
        }
        selectedTiles.clear();
        updateWordDisplay();
    }

    private void shuffleLetters() {
        // Simple shuffle by removing and re-adding tiles
        java.util.Collections.shuffle(letterTiles);
        letterTilesPanel.removeAll();

        JLabel tilesLabel = new JLabel("Your Letter Tiles:");
        tilesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tilesLabel.setForeground(Color.WHITE);
        letterTilesPanel.add(tilesLabel);

        for (LetterTile tile : letterTiles) {
            letterTilesPanel.add(tile);
        }

        letterTilesPanel.revalidate();
        letterTilesPanel.repaint();
    }

    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);

        // Clear status after 3 seconds
        Timer clearTimer = new Timer(3000, e -> statusLabel.setText(" "));
        clearTimer.setRepeats(false);
        clearTimer.start();
    }

    // Update the submitWord method to handle game end scenarios better
    private void submitWord(ActionEvent evt) {
        try {
            String word = currentWord.toString();
            if (word.length() < 3) {
                showStatus("Word must be at least 3 letters long!", Color.RED);
                return;
            }

            // Submit to server
            wordHistoryArea.append("You played: " + word + " (" + calculateWordValue() + " pts)\n");
            wordyImpl.playWord(username, word);

            currentScore += calculateWordValue();
            scoreLabel.setText("Score: " + currentScore);

            clearCurrentWord();
            showStatus("Word submitted successfully!", Color.GREEN);

        } catch (InvalidWord e) {
            // Check if the error is because game ended
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("Game is not active")) {
                if (!gameEndedShown) {
                    handleGameEnd();
                }
                return;
            }

            showStatus("Invalid word: " + currentWord, Color.RED);
            System.out.println("Invalid word exception: " + errorMessage);
        } catch (Exception e) {
            showStatus("Error submitting word", Color.RED);
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Update the timer to handle game end better
    private void startCountdownTimer() {
        gameTimer = new Timer(1000, e -> {
            countdownSeconds--;
            timerField.setText(String.valueOf(countdownSeconds));

            if (countdownSeconds <= 10) {
                timerField.setForeground(new Color(239, 68, 68)); // Red
            } else if (countdownSeconds <= 20) {
                timerField.setForeground(new Color(251, 191, 36)); // Yellow
            }

            if (countdownSeconds <= 0) {
                gameTimer.stop();
                if (!gameEndedShown) {
                    SwingUtilities.invokeLater(() -> handleGameEnd());
                }
            }
        });
        gameTimer.start();
    }

    public static void startGameUI(String username) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new GameUI(username).setVisible(true));
    }
}

