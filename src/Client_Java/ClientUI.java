package Client_Java;

import static Client_Java.Client.wordyImpl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class ClientUI extends javax.swing.JFrame {
    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }
    static String username;
    private Timer onlinePlayersTimer;
    private JTextArea onlinePlayersArea;
    private JLabel onlineCountLabel;
    private JButton refreshButton;
    private JButton quickPlayButton;
    private int lastPlayerCount = 0;

    // Add singleton pattern to prevent multiple instances
    private static ClientUI instance = null;
    private static boolean isDisposing = false;

    // Original UI components
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLabel welcomeMessage;
    private javax.swing.JButton leaderboardButton;
    private javax.swing.JButton exitButton;

    public ClientUI(String username) {
        this.username = username;
        initComponents();
        startOnlinePlayersUpdate();

        // Add window listener to stop timer when window closes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (onlinePlayersTimer != null) {
                    onlinePlayersTimer.stop();
                }
            }
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WordyGame - " + username);
        setResizable(true);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create main panel with proper layout
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        createHeaderPanel();
        createCenterPanel();
        createFooterPanel();

        add(mainPanel);
    }

    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));

        // Welcome message
        welcomeMessage = new JLabel("Welcome, " + username + "!");
        welcomeMessage.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeMessage.setForeground(new Color(51, 51, 51));
        welcomeMessage.setHorizontalAlignment(SwingConstants.CENTER);

        // Game title
        JLabel gameTitle = new JLabel("WordyGame");
        gameTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gameTitle.setForeground(new Color(102, 102, 102));
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout(0, 5));
        titlePanel.setBackground(new Color(245, 245, 245));
        titlePanel.add(welcomeMessage, BorderLayout.CENTER);
        titlePanel.add(gameTitle, BorderLayout.SOUTH);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        centerPanel.setBackground(new Color(245, 245, 245));

        // Left side - Game controls
        createGameControlsPanel(centerPanel);

        // Right side - Online players
        createOnlinePlayersPanel(centerPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void createGameControlsPanel(JPanel parent) {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
        gamePanel.setBackground(Color.WHITE);
        gamePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel gameLabel = new JLabel("Game Options");
        gameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gameLabel.setForeground(new Color(51, 51, 51));
        gameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add spacing
        gamePanel.add(gameLabel);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Quick Play button
        quickPlayButton = createStyledButton("Quick Play", new Color(46, 125, 50));
        quickPlayButton.addActionListener(e -> quickPlayAction());
        gamePanel.add(quickPlayButton);

        gamePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Create Lobby button
        startGameButton = createStyledButton("Create Lobby", new Color(25, 118, 210));
        startGameButton.addActionListener(e -> createLobbyAction());
        gamePanel.add(startGameButton);

        gamePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Leaderboard button
        leaderboardButton = createStyledButton("Leaderboard", new Color(156, 39, 176));
        leaderboardButton.addActionListener(e -> leaderboardButtonActionPerformed());
        gamePanel.add(leaderboardButton);

        parent.add(gamePanel);
    }

    private void createOnlinePlayersPanel(JPanel parent) {
        JPanel onlinePanel = new JPanel(new BorderLayout(0, 15));
        onlinePanel.setBackground(Color.WHITE);
        onlinePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(25, 25, 25, 25)
        ));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel onlineTitle = new JLabel("Online Players");
        onlineTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        onlineTitle.setForeground(new Color(51, 51, 51));

        onlineCountLabel = new JLabel("Players: 0");
        onlineCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        onlineCountLabel.setForeground(new Color(102, 102, 102));

        headerPanel.add(onlineTitle, BorderLayout.WEST);
        headerPanel.add(onlineCountLabel, BorderLayout.EAST);

        // Players list
        onlinePlayersArea = new JTextArea();
        onlinePlayersArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        onlinePlayersArea.setBackground(new Color(250, 250, 250));
        onlinePlayersArea.setForeground(new Color(51, 51, 51));
        onlinePlayersArea.setEditable(false);
        onlinePlayersArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        onlinePlayersArea.setText("Loading players...");

        JScrollPane playersScroll = new JScrollPane(onlinePlayersArea);
        playersScroll.setBorder(new LineBorder(new Color(220, 220, 220), 1));
        playersScroll.setPreferredSize(new Dimension(0, 200));

        // Refresh button
        refreshButton = createSmallButton("Refresh", new Color(96, 125, 139));
        refreshButton.addActionListener(e -> updateOnlinePlayers());

        onlinePanel.add(headerPanel, BorderLayout.NORTH);
        onlinePanel.add(playersScroll, BorderLayout.CENTER);
        onlinePanel.add(refreshButton, BorderLayout.SOUTH);

        parent.add(onlinePanel);
    }

    private void createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(new Color(245, 245, 245));

        exitButton = createSmallButton("Exit", new Color(244, 67, 54));
        exitButton.addActionListener(e -> exitButtonActionPerformed());

        footerPanel.add(exitButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = backgroundColor;
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private JButton createSmallButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = backgroundColor;
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void startOnlinePlayersUpdate() {
        // Update online players every 3 seconds
        onlinePlayersTimer = new Timer(3000, e -> updateOnlinePlayers());
        onlinePlayersTimer.start();

        // Initial update
        updateOnlinePlayers();
    }

    private void updateOnlinePlayers() {
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                try {
                    return wordyImpl.playerInGameList();
                } catch (Exception e) {
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void done() {
                try {
                    String playerList = get();
                    if (playerList != null && !playerList.startsWith("Error:")) {
                        displayOnlinePlayers(playerList);
                    } else {
                        onlinePlayersArea.setText("Unable to fetch players\n" + (playerList != null ? playerList : ""));
                        onlineCountLabel.setText("Players: --");
                    }
                } catch (Exception e) {
                    onlinePlayersArea.setText("Connection error:\n" + e.getMessage());
                    onlineCountLabel.setText("Players: --");
                }
            }
        };
        worker.execute();
    }

    // Fixed online players display
    private void displayOnlinePlayers(String playerListString) {
        System.out.println("Raw player list from server: [" + playerListString + "]"); // Debug output

        if (playerListString == null || playerListString.trim().isEmpty() || playerListString.equals("[]")) {
            onlinePlayersArea.setText("No players online");
            onlineCountLabel.setText("Players: 0");
            lastPlayerCount = 0;
            return;
        }

        // Clean up the string - remove brackets and split by comma
        String cleanedList = playerListString.trim();
        if (cleanedList.startsWith("[")) {
            cleanedList = cleanedList.substring(1);
        }
        if (cleanedList.endsWith("]")) {
            cleanedList = cleanedList.substring(0, cleanedList.length() - 1);
        }

        // Handle empty list after cleaning
        if (cleanedList.trim().isEmpty()) {
            onlinePlayersArea.setText("No players online");
            onlineCountLabel.setText("Players: 0");
            lastPlayerCount = 0;
            return;
        }

        // Split by comma and clean each name
        String[] players = cleanedList.split(",");
        List<String> playerList = Arrays.stream(players)
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(toList());

        int currentPlayerCount = playerList.size();

        // Display players with simple formatting
        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < playerList.size(); i++) {
            String player = playerList.get(i);

            if (player.equals(username)) {
                displayText.append(player).append(" (You)");
            } else {
                displayText.append(player);
            }

            if (i < playerList.size() - 1) {
                displayText.append("\n");
            }
        }

        onlinePlayersArea.setText(displayText.toString());
        onlineCountLabel.setText("Players: " + currentPlayerCount);

        // Show notification for player count changes
        if (currentPlayerCount != lastPlayerCount) {
            if (currentPlayerCount > lastPlayerCount) {
                showPlayerNotification("New player joined! (" + currentPlayerCount + " total)");
            } else if (currentPlayerCount < lastPlayerCount) {
                showPlayerNotification("Player left (" + currentPlayerCount + " remaining)");
            }

            // Enable/disable quick play based on player count
            if (currentPlayerCount >= 2) {
                quickPlayButton.setBackground(new Color(76, 175, 80));
                quickPlayButton.setText("Quick Play (" + currentPlayerCount + " players)");
            } else {
                quickPlayButton.setBackground(new Color(46, 125, 50));
                quickPlayButton.setText("Quick Play");
            }
        }

        lastPlayerCount = currentPlayerCount;
    }

    // Simple notification system
    private void showPlayerNotification(String message) {
        SwingUtilities.invokeLater(() -> {
            String currentTitle = getTitle();
            setTitle(message + " - " + currentTitle);

            // Reset title after 3 seconds
            Timer titleTimer = new Timer(3000, e -> {
                setTitle("WordyGame - " + username);
                ((Timer) e.getSource()).stop();
            });
            titleTimer.start();
        });
    }

    private void quickPlayAction() {
        quickPlayButton.setEnabled(false);
        quickPlayButton.setText("Joining...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    return wordyImpl.joinLobby(username);
                } catch (Exception e) {
                    System.err.println("Error joining lobby: " + e.getMessage());
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        dispose();
                        if (onlinePlayersTimer != null) {
                            onlinePlayersTimer.stop();
                        }
                        LobbyUI.startLobby(username);
                    } else {
                        JOptionPane.showMessageDialog(ClientUI.this,
                            "Unable to join game. Please try again.",
                            "Connection Error",
                            JOptionPane.ERROR_MESSAGE);
                        quickPlayButton.setEnabled(true);
                        quickPlayButton.setText("Quick Play");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ClientUI.this,
                        "Error: " + e.getMessage(),
                        "System Error",
                        JOptionPane.ERROR_MESSAGE);
                    quickPlayButton.setEnabled(true);
                    quickPlayButton.setText("Quick Play");
                }
            }
        };
        worker.execute();
    }

    private void createLobbyAction() {
        dispose();
        if (onlinePlayersTimer != null) {
            onlinePlayersTimer.stop();
        }
        LobbyUI.startLobby(username);
    }

    private void exitButtonActionPerformed() {
        if (onlinePlayersTimer != null) {
            onlinePlayersTimer.stop();
        }
        wordyImpl.exit(username);
        dispose();
    }

    private void leaderboardButtonActionPerformed() {
        LeaderboardUI.startLeaderboardUI(username);
    }

    public static void startClientUI(String username) {
        System.out.println("StartClientUI called for: " + username);

        SwingUtilities.invokeLater(() -> {
            // Check if an instance already exists and is visible
            if (instance != null && instance.isVisible() && !isDisposing) {
                System.out.println("ClientUI instance already exists, bringing to front");
                instance.toFront();
                instance.requestFocus();
                return;
            }

            // If we're disposing, wait a moment then create new instance
            if (isDisposing) {
                Timer waitTimer = new Timer(100, e -> {
                    if (!isDisposing) {
                        createNewInstance(username);
                    }
                    ((Timer) e.getSource()).stop();
                });
                waitTimer.start();
                return;
            }

            createNewInstance(username);
        });
    }

    private static void createNewInstance(String username) {
        // Close existing instance if it exists
        if (instance != null) {
            instance.dispose();
        }

        // Create new instance
        instance = new ClientUI(username);
        instance.setVisible(true);
        System.out.println("New ClientUI instance created for: " + username);
    }

    @Override
    public void dispose() {
        isDisposing = true;

        if (onlinePlayersTimer != null) {
            onlinePlayersTimer.stop();
        }

        super.dispose();

        // Clear instance reference after disposal
        Timer cleanupTimer = new Timer(50, e -> {
            if (instance == this) {
                instance = null;
            }
            isDisposing = false;
            ((Timer) e.getSource()).stop();
        });
        cleanupTimer.start();
    }
}
