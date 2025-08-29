package Client_Java;

import corbaGame.*;
import javax.swing.*;
import Client_Java.util.UIUtils;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import static Client_Java.Client.wordyImpl;

public class GameUI extends javax.swing.JFrame {
    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }
    private static String username;
    private JLabel timerField;
    private Timer gameStateTimer;
    private Timer synchronizedTimer;

    // UI Components
    private JPanel letterTilesPanel;
    private JPanel kbRow1, kbRow2, kbRow3; // keyboard rows
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
    private boolean hasBeenInGame = false;

    // ---- keyboard tile sizing (tweak if you want smaller/larger) ----
    private static final int TILE_SIZE = 56;     // 56px square keys (try 48 for smaller)
    private static final int TILE_FONT = 20;     // 20px letter inside the key (try 18 for smaller)

    public GameUI(String username) {
        GameUI.username = username;
        this.letterTiles = new ArrayList<>();
        this.selectedTiles = new ArrayList<>();
        this.currentWord = new StringBuilder();

        // Enable font anti-aliasing for crisper text
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext","true");
        // Try Nimbus Look & Feel for a cleaner, modern look (fallback gracefully)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {}
        // Make UI a bit more compact globally
        shrinkUIDefaultFonts(0.82);
        initializeUI();
        addListeners();
    }

    /**
     * Shrink (or expand) all default UI fonts by a given scale.
     * This updates Nimbus defaults so labels, buttons, text fields, etc. are consistent.
     */
    private static void shrinkUIDefaultFonts(double scale) {
        try {
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            java.util.Enumeration<?> keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object val = defaults.get(key);
                if (val instanceof Font) {
                    Font f = (Font) val;
                    int newSize = (int)Math.max(11, Math.round(f.getSize2D() * (float)scale));
                    defaults.put(key, f.deriveFont((float)newSize));
                }
            }
        } catch (Exception ignored) {}
    }

    private void initializeUI() {
        setTitle("WordyGame - Modern Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(45, 52, 70));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        createHeaderPanel(mainPanel);
        createGamePanel(mainPanel);

        add(mainPanel);
    }

    private void createHeaderPanel(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(31, 41, 55));
        headerPanel.setBorder(new LineBorder(new Color(75, 85, 99), 2, true));
        headerPanel.setPreferredSize(new Dimension(0, 80));

        // Title and timer
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(31, 41, 55));
        JLabel titleLabel = new JLabel("WordyGame");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        // Timer and score panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setBackground(new Color(31, 41, 55));

        timerField = new JLabel("30");
        timerField.setFont(new Font("Segoe UI", Font.BOLD, 19));
        timerField.setForeground(new Color(239, 68, 68));
        timerField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(239, 68, 68), 2, true),
                new EmptyBorder(5, 10, 5, 10)
        ));

        scoreLabel = new JLabel("Total Letters: 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scoreLabel.setForeground(new Color(34, 197, 94));
        scoreLabel.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel currentPlayerLabel = new JLabel("Player: " + username);
        currentPlayerLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        currentPlayerLabel.setForeground(new Color(168, 162, 158));

        infoPanel.add(currentPlayerLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(timerField);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(infoPanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createGamePanel(JPanel mainPanel) {
        JPanel gamePanel = new JPanel(new BorderLayout(15, 15));
        gamePanel.setBackground(new Color(45, 52, 70));

        // Left side - Game area
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(new Color(45, 52, 70));
        leftPanel.setPreferredSize(new Dimension(750, 0));

        // Word construction area
        createWordConstructionPanel(leftPanel);

        // Letter tiles area (keyboard layout)
        createLetterTilesPanel(leftPanel);

        // Control buttons
        createGameControlPanel(leftPanel);

        // Right side - Player info and game history
        createPlayerInfoPanel(gamePanel);

        gamePanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
    }

    private void createWordConstructionPanel(JPanel leftPanel) {
        JPanel wordConstructionPanel = new JPanel(new BorderLayout(10, 10));
        wordConstructionPanel.setBackground(new Color(55, 65, 81));
        wordConstructionPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(107,114,128), 2, true),
                new EmptyBorder(12,12,12,12)
        ));
        wordConstructionPanel.setPreferredSize(new Dimension(0, 100));

        JLabel wordLabel = new JLabel("Construct Your Word:");
        wordLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        wordLabel.setForeground(Color.WHITE);

        wordInputField = new JTextField();
        wordInputField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        wordInputField.setBackground(new Color(17, 24, 39));
        wordInputField.setForeground(Color.WHITE);
        wordInputField.setCaretColor(Color.WHITE);
        wordInputField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(59,130,246),2,true),
                new EmptyBorder(8,12,8,12)
        ));
        wordInputField.setEditable(false);

        wordValueLabel = new JLabel("Word Length: 0 letters");
        wordValueLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        wordValueLabel.setForeground(new Color(168, 162, 158));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(55, 65, 81));
        topPanel.add(wordLabel, BorderLayout.WEST);
        topPanel.add(wordValueLabel, BorderLayout.EAST);

        wordConstructionPanel.add(topPanel, BorderLayout.NORTH);
        wordConstructionPanel.add(wordInputField, BorderLayout.CENTER);

        leftPanel.add(wordConstructionPanel, BorderLayout.NORTH);
    }

    // --- Keyboard-style letter area (3 rows, small fixed-size keys) ---
    private void createLetterTilesPanel(JPanel leftPanel) {
        letterTilesPanel = new JPanel();
        letterTilesPanel.setLayout(new BoxLayout(letterTilesPanel, BoxLayout.Y_AXIS));
        letterTilesPanel.setBackground(new Color(55, 65, 81));
        letterTilesPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(107,114,128), 2, true),
                new EmptyBorder(12,12,12,12)
        ));

        kbRow1 = makeKBRow(0);   // top
        kbRow2 = makeKBRow(20);  // home (indented)
        kbRow3 = makeKBRow(40);  // bottom (more indented)

        letterTilesPanel.add(Box.createVerticalGlue());
        letterTilesPanel.add(kbRow1);
        letterTilesPanel.add(Box.createVerticalStrut(8));
        letterTilesPanel.add(kbRow2);
        letterTilesPanel.add(Box.createVerticalStrut(8));
        letterTilesPanel.add(kbRow3);
        letterTilesPanel.add(Box.createVerticalGlue());

        leftPanel.add(letterTilesPanel, BorderLayout.CENTER);
    }

    private JPanel makeKBRow(int leftIndentPx) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        row.setOpaque(false);
        if (leftIndentPx > 0) {
            row.setBorder(new EmptyBorder(0, leftIndentPx, 0, 0));
        }
        return row;
    }

    private void createGameControlPanel(JPanel leftPanel) {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
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
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setForeground(new Color(251, 191, 36));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(45, 52, 70));
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel fullControlPanel = new JPanel(new BorderLayout());
        fullControlPanel.setBackground(new Color(45, 52, 70));
        fullControlPanel.add(controlPanel, BorderLayout.CENTER);
        fullControlPanel.add(statusPanel, BorderLayout.SOUTH);

        leftPanel.add(fullControlPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(color.darker(), 1),
                new EmptyBorder(8, 16, 8, 16)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    private void createPlayerInfoPanel(JPanel gamePanel) {
        JPanel playerInfoPanel = new JPanel(new BorderLayout(10, 10));
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
        playersLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        playersLabel.setForeground(Color.WHITE);

        playerListArea = new JTextArea();
        playerListArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
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

        JLabel historyLabel = new JLabel("All Players' Words");
        historyLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        historyLabel.setForeground(Color.WHITE);

        wordHistoryArea = new JTextArea();
        wordHistoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        wordHistoryArea.setBackground(new Color(17, 24, 39));
        wordHistoryArea.setForeground(new Color(209, 213, 219));
        wordHistoryArea.setEditable(false);
        wordHistoryArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        wordHistoryArea.setLineWrap(true);
        wordHistoryArea.setWrapStyleWord(true);
        wordHistoryArea.setText("Submitted words from all players will appear here...");

        JScrollPane historyScroll = new JScrollPane(wordHistoryArea);
        historyScroll.setBorder(null);

        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(historyScroll, BorderLayout.CENTER);

        playerInfoPanel.add(playersPanel, BorderLayout.NORTH);
        playerInfoPanel.add(historyPanel, BorderLayout.CENTER);

        gamePanel.add(playerInfoPanel, BorderLayout.EAST);
    }

    // Letter tile class for interactive tiles
    private class LetterTile extends JButton {
        private final char letter;
        private boolean isSelected;

        public LetterTile(char letter) {
            this.letter = letter;
            this.isSelected = false;
            setupTile();
        }

        private void setupTile() {
            setText(String.valueOf(Character.toUpperCase(letter)));
            setHorizontalAlignment(SwingConstants.CENTER);
            // fixed, compact size so they never dominate
            setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
            setMinimumSize(new Dimension(TILE_SIZE, TILE_SIZE));
            setMaximumSize(new Dimension(TILE_SIZE, TILE_SIZE));
            setFont(new Font("Segoe UI", Font.BOLD, TILE_FONT));
            setBackground(new Color(251, 191, 36));
            setForeground(new Color(92, 77, 192));
            setBorder(new LineBorder(new Color(217, 119, 6), 2, true));
            setMargin(new Insets(0,0,0,0));
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

        public void deselect() {
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
            String word = currentWord.toString();
            int lastIndex = word.lastIndexOf(Character.toUpperCase(letter));
            if (lastIndex >= 0) {
                currentWord.deleteCharAt(lastIndex);
                updateWordDisplay();
            }
        }

        public boolean isSelected() {
            return isSelected;
        }
    }

    private void updateWordDisplay() {
        wordInputField.setText(currentWord.toString());
        int wordLength = currentWord.length();
        wordValueLabel.setText("Word Length: " + wordLength + " letters");
    }

    private void populateLetterTiles(String letters) {
        System.out.println("GameUI: populateLetterTiles called with: '" + letters + "'");

        // clear previous
        kbRow1.removeAll();
        kbRow2.removeAll();
        kbRow3.removeAll();
        letterTiles.clear();
        selectedTiles.clear();
        currentWord.setLength(0);

        String lettersToUse;
        boolean isTestMode = false;

        if (letters == null || letters.trim().isEmpty()) {
            lettersToUse = "TESTWORDABCDE";
            isTestMode = true;
        } else {
            lettersToUse = letters.replaceAll("[^A-Za-z]", "").toUpperCase();
            if (lettersToUse.isEmpty()) {
                lettersToUse = "GAMETESTABCDE";
                isTestMode = true;
            }
        }

        if (lettersToUse.length() < 6) {
            lettersToUse = lettersToUse + "ABCDEFGHIJKLM";
            lettersToUse = lettersToUse.substring(0, 13);
            isTestMode = true;
        }

        int tilesCreated = 0;
        for (char c : lettersToUse.toCharArray()) {
            if (Character.isLetter(c)) {
                LetterTile tile = new LetterTile(c);
                letterTiles.add(tile);
                tilesCreated++;
            }
        }

        // place tiles into balanced keyboard-like rows
        layoutTilesBalanced();

        if (isTestMode) {
            showStatus("Using test letters - server may be disconnected", new Color(251, 191, 36));
        } else {
            showStatus("Game ready! " + tilesCreated + " letters loaded.", new Color(34, 197, 94));
        }

        updateWordDisplay();

        SwingUtilities.invokeLater(() -> {
            letterTilesPanel.revalidate();
            letterTilesPanel.repaint();
        });
    }

    // Distribute tiles across 3 rows in a balanced way (weighted 10-9-7 like a keyboard)
    private void layoutTilesBalanced() {
        kbRow1.removeAll();
        kbRow2.removeAll();
        kbRow3.removeAll();

        int n = letterTiles.size();
        int[] counts = distributeAcrossRows(n); // counts[0]=top, [1]=home, [2]=bottom

        int idx = 0;
        for (int i = 0; i < counts[0] && idx < n; i++) kbRow1.add(letterTiles.get(idx++));
        for (int i = 0; i < counts[1] && idx < n; i++) kbRow2.add(letterTiles.get(idx++));
        for (int i = 0; i < counts[2] && idx < n; i++) kbRow3.add(letterTiles.get(idx++));
    }

    // Keep rows visually even; avoid single-key middle rows whenever possible
    private int[] distributeAcrossRows(int n) {
        if (n <= 0) return new int[]{0,0,0};
        int max1 = 10, max2 = 9, max3 = 7;
        double totalW = max1 + max2 + max3; // 26

        int r1 = (int)Math.round(n * (max1 / totalW));
        int r2 = (int)Math.round(n * (max2 / totalW));
        int r3 = n - r1 - r2;

        // Cap to maximums and push overflow forward
        if (r1 > max1) { int over = r1 - max1; r1 = max1; r2 += over; }
        if (r2 > max2) { int over = r2 - max2; r2 = max2; r3 += over; }
        if (r3 > max3) {
            int over = r3 - max3; r3 = max3;
            int add2 = Math.min(over, max2 - r2); r2 += add2; over -= add2;
            int add1 = Math.min(over, max1 - r1); r1 += add1; over -= add1;
        }

        // Avoid lonely middle row if we have enough tiles
        if (r2 == 1 && n >= 4) {
            if (r1 >= 3) { r1--; r2++; }
            else if (r3 >= 2) { r3--; r2++; }
        }
        // Avoid an empty middle row between two non-empty rows
        if (r2 == 0 && r1 > 0 && r3 > 0) {
            if (r1 >= r3 && r1 > 1) { r1--; r2++; }
            else if (r3 > 1) { r3--; r2++; }
        }
        // Prefer at least 2 keys per shown row when possible
        if (n >= 5) {
            if (r1 == 1 && r2 > 2) { r2--; r1++; }
            if (r3 == 1 && r2 > 2) { r2--; r3++; }
        }
        return new int[]{r1, r2, r3};
    }

    public void addListeners() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                statusLabel.setText("Loading game...");
                statusLabel.setForeground(new Color(251, 191, 36));

                new Thread(() -> {
                    try {
                        System.out.println("GameUI: Loading game for user: " + username);
                        Thread.sleep(1000); // brief delay to let server prepare
                        SwingUtilities.invokeLater(() -> initializeGameAfterJoin());
                    } catch (Exception ex) {
                        System.out.println("GameUI: Error loading game: " + ex.getMessage());
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText("Error loading game: " + ex.getMessage());
                            statusLabel.setForeground(new Color(239, 68, 68));
                        });
                    }
                }).start();
            }

            @Override public void windowClosing(WindowEvent e) { cleanupResources(); }
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });

        submitWordButton.addActionListener(this::submitWord);
        clearWordButton.addActionListener(e -> clearCurrentWord());
        shuffleLettersButton.addActionListener(e -> shuffleLetters());
    }

    private void initializeGameAfterJoin() {
        statusLabel.setText("Loading game data...");
        statusLabel.setForeground(new Color(34, 197, 94));

        // Run server calls off the EDT to keep UI responsive
        UIUtils.runAsync(() -> {
            try { Thread.sleep(300); } catch (InterruptedException ignored) {}
            String playerList = Client.safeServerCall(wordy::playerInGameList, "");
            String letters = Client.safeServerCall(wordy::getGeneratedLetter, "");
            return new String[]{playerList, letters};
        }, result -> {
            String playerList = result[0];
            String letters = result[1];

            if (playerList != null && !playerList.trim().isEmpty() &&
                    !playerList.equals("[]") && !playerList.equals("[[]]")) {

                String formattedList = playerList
                        .replace("[[", "").replace("]]", "")
                        .replace("[", "").replace("]", "")
                        .replace(",", "\n").trim();
                playerListArea.setText(formattedList);
                statusLabel.setText("Game ready! " + formattedList.split("\n").length + " player(s) in.");
                statusLabel.setForeground(new Color(34, 197, 94));
            } else {
                playerListArea.setText("Loading other players...");
                statusLabel.setText("Game ready! Waiting for other players...");
            }

            populateLetterTiles(letters);
            startGameStateMonitoring();
            startSynchronizedTimer();
        });
    }

    private void showStatus(String message, Color color) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(message);
            statusLabel.setForeground(color);

            Timer clearTimer = new Timer(3000, e -> {
                if (statusLabel.getText().equals(message)) {
                    statusLabel.setText(" ");
                }
                ((Timer) e.getSource()).stop();
            });
            clearTimer.start();
        });
    }

    private void showLobbyJoinFailureDialog() {
        JDialog failureDialog = new JDialog(this, "Lobby Join Failed", true);
        failureDialog.setSize(450, 250);
        failureDialog.setLocationRelativeTo(this);
        failureDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel dialogPanel = new JPanel(new BorderLayout(15, 15));
        dialogPanel.setBackground(new Color(45, 52, 70));
        dialogPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Unable to Join Game Lobby", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(239, 68, 68));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
                "Failed to join the game lobby. This could be because:<br><br>" +
                "• The server is not running<br>" +
                "• Network connection issues<br>" +
                "• The lobby is full<br>" +
                "• Server authentication problems<br><br>" +
                "Please check the server status and try again.</div></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        messageLabel.setForeground(new Color(209, 213, 219));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(45, 52, 70));

        JButton retryButton = createStyledButton("Retry Join", new Color(34, 197, 94));
        JButton backButton = createStyledButton("Back to Main Menu", new Color(107, 114, 128));

        retryButton.addActionListener(e -> {
            failureDialog.dispose();
            retryLobbyJoin();
        });

        backButton.addActionListener(e -> {
            failureDialog.dispose();
            backToLobby();
        });

        buttonPanel.add(retryButton);
        buttonPanel.add(backButton);

        dialogPanel.add(titleLabel, BorderLayout.NORTH);
        dialogPanel.add(messageLabel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        failureDialog.add(dialogPanel);
        failureDialog.setVisible(true);
    }

    private void retryLobbyJoin() {
        try {
            statusLabel.setText("Retrying lobby join...");
            statusLabel.setForeground(new Color(251, 191, 36));

            boolean joinedLobby = wordyImpl.joinLobby(username);

            if (joinedLobby) {
                statusLabel.setText("Successfully joined lobby!");
                statusLabel.setForeground(new Color(34, 197, 94));

                submitWordButton.setEnabled(true);
                clearWordButton.setEnabled(true);
                shuffleLettersButton.setEnabled(true);
                wordInputField.setEnabled(true);

                String letters = wordyImpl.getGeneratedLetter();
                populateLetterTiles(letters);

            } else {
                showLobbyJoinFailureDialog();
            }

        } catch (Exception ex) {
            statusLabel.setText("Retry failed: " + ex.getMessage());
            statusLabel.setForeground(new Color(239, 68, 68));
            showLobbyJoinFailureDialog();
        }
    }

    private void backToLobby() {
        try {
            cleanupResources();
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    ClientUI.startClientUI(username);
                } catch (Exception e) {
                    System.out.println("Error returning to lobby: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cleanupResources() {
        try {
            if (gameStateTimer != null) {
                gameStateTimer.stop();
            }
            if (synchronizedTimer != null) {
                synchronizedTimer.stop();
            }
            wordyImpl.leaveGame(username);
        } catch (Exception e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
    }

    private void submitWord(ActionEvent e) {
        String word = currentWord.toString().trim();
        if (word.isEmpty()) { showStatus("Please create a word first!", Color.RED); return; }
        if (word.length() < 3) { showStatus("Word must be at least 3 letters long!", Color.RED); return; }

        // Run playWord off the EDT
        UIUtils.runAsync(() -> {
            try {
                wordyImpl.playWord(username, word);
                return "OK";
            } catch (InvalidWord ex) {
                return "INVALID:" + ex.message;
            } catch (Exception ex) {
                return "ERR:" + ex.getMessage();
            }
        }, result -> {
            if ("OK".equals(result)) {
                int wordLength = currentWord.length();
                showStatus("Word '" + word + "' submitted successfully! (" + wordLength + " letters)", new Color(34, 197, 94));
                clearCurrentWordSilently();
                updateWordHistoryAsync();
            } else if (result.startsWith("INVALID:")) {
                showStatus("Invalid word: " + result.substring("INVALID:".length()), Color.RED);
            } else if (result.startsWith("ERR:")) {
                showStatus("Error submitting word: " + result.substring("ERR:".length()), Color.RED);
            }
        });
    }

    // New method to update word history with all players' submitted words
    private void updateWordHistory() { updateWordHistoryAsync(); }

    private void updateWordHistoryAsync() {
        UIUtils.runAsync(() -> {
            try {
                return wordyImpl.getValidWordFromClients();
            } catch (Exception e) {
                System.out.println("Error updating word history: " + e.getMessage());
                return null;
            }
        }, allWords -> {
            if (allWords != null && !allWords.trim().isEmpty()) {
                StringBuilder formattedWords = new StringBuilder();
                formattedWords.append("Submitted Words:\n\n");
                String[] lines = allWords.split("\n");
                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(":");
                    if (parts.length >= 2) {
                        String playerName = parts[0].trim();
                        String word = parts[1].trim();
                        formattedWords.append("• ").append(playerName).append(": ").append(word).append("\n");
                    } else {
                        formattedWords.append("• ").append(line.trim()).append("\n");
                    }
                }
                wordHistoryArea.setText(formattedWords.toString());
            } else {
                wordHistoryArea.setText("No words submitted yet.\nBe the first to submit a word!");
            }
        });
    }

    private void startGameStateMonitoring() {
        gameStateTimer = new Timer(2000, e -> {
            try {
                updatePlayerList();
                updateWordHistory(); // Also monitor word submissions from all players
            } catch (Exception ex) {
                // Silently handle connection errors
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
            } else {
                SwingUtilities.invokeLater(() -> playerListArea.setText("No players in game"));
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> playerListArea.setText("Error loading players"));
        }
    }

    private void startSynchronizedTimer() {
        if (synchronizedTimer != null) {
            synchronizedTimer.stop();
        }

        synchronizedTimer = new Timer(500, e -> {
            try {
                // Use safe server calls to get timer information
                double timer = Client.safeGetTimer();
                double lobbyCount = Client.safeGetLobbyCount();
                boolean isPlayerInGame = Client.safeIsPlayerInGame(username);

                if (isPlayerInGame && timer > 0) {
                    hasBeenInGame = true;
                }

                SwingUtilities.invokeLater(() -> {
                    // Transition first when timer is up and we were in a game
                    if (timer <= 0 && hasBeenInGame) {
                        timerField.setText("GAME OVER");
                        timerField.setForeground(new Color(239, 68, 68));
                        showStatus("Game finished! Loading results...", new Color(34, 197, 94));

                        synchronizedTimer.stop();
                        Timer transitionTimer = new Timer(1200, evt -> {
                            transitionToGameResults();
                            ((Timer) evt.getSource()).stop();
                        });
                        transitionTimer.setRepeats(false);
                        transitionTimer.start();

                        submitWordButton.setEnabled(false);
                        clearWordButton.setEnabled(false);
                        shuffleLettersButton.setEnabled(false);
                        return;
                    }

                    if (isPlayerInGame && timer > 0) {
                        // Active game
                        timerField.setText("Game: " + (int)timer);
                        timerField.setForeground(new Color(239, 68, 68));
                        if (timer <= 5) {
                            showStatus("Time running out!", new Color(239, 68, 68));
                        }
                    } else if (lobbyCount > 0 && timer > 0) {
                        // Lobby countdown
                        timerField.setText("Lobby: " + (int)timer);
                        timerField.setForeground(new Color(251, 191, 36));
                    } else {
                        // Waiting/idle
                        timerField.setText("Waiting...");
                        timerField.setForeground(new Color(168, 162, 158));
                    }
                });

            } catch (Exception ex) {
                System.out.println("GameUI: Error syncing with server: " + ex.getMessage());
                SwingUtilities.invokeLater(() -> {
                    timerField.setText("Disconnected");
                    timerField.setForeground(Color.RED);
                    showStatus("Connection lost - trying to reconnect...", Color.RED);
                });
            }
        });

        synchronizedTimer.start();
    }

    private void clearCurrentWord() {
        for (LetterTile tile : new ArrayList<>(selectedTiles)) {
            tile.deselect();
        }
        selectedTiles.clear();
        currentWord.setLength(0);
        updateWordDisplay();
        showStatus("Word cleared", new Color(168, 162, 158));
    }

    private void clearCurrentWordSilently() {
        for (LetterTile tile : new ArrayList<>(selectedTiles)) {
            tile.deselect();
        }
        selectedTiles.clear();
        currentWord.setLength(0);
        updateWordDisplay();
    }

    private void shuffleLetters() {
        if (letterTiles.isEmpty()) {
            showStatus("No letters to shuffle!", Color.RED);
            return;
        }

        clearCurrentWord();

        java.util.Collections.shuffle(letterTiles);
        layoutTilesBalanced();

        letterTilesPanel.revalidate();
        letterTilesPanel.repaint();

        showStatus("Letters shuffled!", new Color(59, 130, 246));
    }

    private void transitionToGameResults() {
        SwingUtilities.invokeLater(() -> {
            // Clean up resources
            cleanupResources();

            // Close this window
            this.dispose();

            // Open game results
            GameResultsUI.startGameResults(username);
        });
    }

    public static void startGameUI(String username) {
        SwingUtilities.invokeLater(() -> {
            try {
                new GameUI(username).setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error starting game: " + e.getMessage(),
                        "Game Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
