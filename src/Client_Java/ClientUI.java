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
    static String username;
    private Timer onlinePlayersTimer;
    private JTextArea onlinePlayersArea;
    private JLabel onlineCountLabel;
    private JButton refreshButton;
    private JButton quickPlayButton;
    private int lastPlayerCount = 0;

    // Original UI components
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLabel welcomeMessage;
    private javax.swing.JLabel logo;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLabel backgroundPane;
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
        backgroundPanel = new javax.swing.JPanel();
        startGameButton = new javax.swing.JButton();
        welcomeMessage = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        backgroundPane = new javax.swing.JLabel();
        leaderboardButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WordyGame - Main Menu (" + username + ")");
        setResizable(false);

        backgroundPanel.setBackground(new java.awt.Color(204, 153, 255));
        backgroundPanel.setLayout(null); // Use absolute layout for precise positioning

        // Welcome message with username
        welcomeMessage.setFont(new java.awt.Font("Segoe UI", 1, 32));
        welcomeMessage.setForeground(new java.awt.Color(255, 255, 255));
        welcomeMessage.setText("Welcome " + username + "!");
        welcomeMessage.setBounds(46, 25, 600, 40);
        backgroundPanel.add(welcomeMessage);

        // Game title
        JLabel gameTitle = new JLabel("WORD GAME BETA v 3.32.71");
        gameTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        gameTitle.setForeground(new java.awt.Color(255, 255, 255));
        gameTitle.setBounds(46, 70, 400, 30);
        backgroundPanel.add(gameTitle);

        // Logo
        logo.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png"));
        logo.setBounds(35, 120, 400, 300);
        backgroundPanel.add(logo);

        // Online Players Panel
        createOnlinePlayersPanel();

        // Game action buttons
        createGameButtons();

        // Other buttons
        createOtherButtons();

        // Background
        backgroundPane.setIcon(new javax.swing.ImageIcon("src/Client_Java/17250835.png"));
        backgroundPane.setBounds(0, 0, 905, 524);
        backgroundPanel.add(backgroundPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 905, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void createOnlinePlayersPanel() {
        // Online Players Panel
        JPanel onlinePanel = new JPanel();
        onlinePanel.setLayout(new BorderLayout(5, 5));
        onlinePanel.setBackground(new Color(31, 41, 55));
        onlinePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(59, 130, 246), 3, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        onlinePanel.setBounds(460, 120, 280, 300);

        // Header
        JLabel onlineTitle = new JLabel("🌐 ONLINE PLAYERS", SwingConstants.CENTER);
        onlineTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        onlineTitle.setForeground(Color.WHITE);

        onlineCountLabel = new JLabel("Players: 0", SwingConstants.CENTER);
        onlineCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        onlineCountLabel.setForeground(new Color(34, 197, 94));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(31, 41, 55));
        headerPanel.add(onlineTitle, BorderLayout.NORTH);
        headerPanel.add(onlineCountLabel, BorderLayout.SOUTH);

        // Players list
        onlinePlayersArea = new JTextArea();
        onlinePlayersArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        onlinePlayersArea.setBackground(new Color(17, 24, 39));
        onlinePlayersArea.setForeground(new Color(209, 213, 219));
        onlinePlayersArea.setEditable(false);
        onlinePlayersArea.setBorder(new EmptyBorder(5, 5, 5, 5));
        onlinePlayersArea.setText("Loading players...");

        JScrollPane playersScroll = new JScrollPane(onlinePlayersArea);
        playersScroll.setBorder(null);
        playersScroll.setPreferredSize(new Dimension(0, 180));

        // Refresh button
        refreshButton = new JButton("🔄 Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshButton.setBackground(new Color(59, 130, 246));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorder(null);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> updateOnlinePlayers());

        onlinePanel.add(headerPanel, BorderLayout.NORTH);
        onlinePanel.add(playersScroll, BorderLayout.CENTER);
        onlinePanel.add(refreshButton, BorderLayout.SOUTH);

        backgroundPanel.add(onlinePanel);
    }

    private void createGameButtons() {
        // Quick Play button - automatically creates lobby and starts game when 2+ players
        quickPlayButton = new JButton("🚀 QUICK PLAY");
        quickPlayButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        quickPlayButton.setBackground(new Color(34, 197, 94));
        quickPlayButton.setForeground(Color.WHITE);
        quickPlayButton.setBorder(BorderFactory.createRaisedBevelBorder());
        quickPlayButton.setFocusPainted(false);
        quickPlayButton.setBounds(760, 150, 130, 50);
        quickPlayButton.addActionListener(e -> quickPlayAction());
        backgroundPanel.add(quickPlayButton);

        // Original Start Game button (now creates lobby manually)
        startGameButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18));
        startGameButton.setForeground(new java.awt.Color(238, 241, 255));
        startGameButton.setText("CREATE LOBBY");
        startGameButton.setBorder(null);
        startGameButton.setContentAreaFilled(false);
        startGameButton.setBounds(760, 220, 130, 40);
        startGameButton.addActionListener(e -> createLobbyAction());
        backgroundPanel.add(startGameButton);
    }

    private void createOtherButtons() {
        leaderboardButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14));
        leaderboardButton.setForeground(new java.awt.Color(238, 241, 255));
        leaderboardButton.setText("LEADERBOARD");
        leaderboardButton.setBorder(null);
        leaderboardButton.setContentAreaFilled(false);
        leaderboardButton.setBounds(760, 280, 130, 30);
        leaderboardButton.addActionListener(e -> leaderboardButtonActionPerformed());
        backgroundPanel.add(leaderboardButton);

        exitButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14));
        exitButton.setForeground(new java.awt.Color(238, 241, 255));
        exitButton.setText("EXIT");
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.setBounds(760, 320, 130, 30);
        exitButton.addActionListener(e -> exitButtonActionPerformed());
        backgroundPanel.add(exitButton);
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

    private void displayOnlinePlayers(String playerListString) {
        if (playerListString == null || playerListString.trim().isEmpty()) {
            onlinePlayersArea.setText("No players online");
            onlineCountLabel.setText("Players: 0");
            lastPlayerCount = 0;
            return;
        }

        // Parse the player list (assuming comma-separated)
        String[] players = playerListString.split(",");
        List<String> playerList = Arrays.asList(players);

        // Filter out empty strings
        playerList = playerList.stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(toList());

        int currentPlayerCount = playerList.size();

        // Update UI
        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < playerList.size(); i++) {
            String player = playerList.get(i);
            displayText.append("• ").append(player);
            if (player.equals(username)) {
                displayText.append(" (You)");
            }
            if (i < playerList.size() - 1) {
                displayText.append("\n");
            }
        }

        onlinePlayersArea.setText(displayText.toString());
        onlineCountLabel.setText("Players: " + currentPlayerCount);

        // Check for automatic game start (2+ players including current user)
        if (currentPlayerCount >= 2 && currentPlayerCount > lastPlayerCount) {
            // Show notification that there are enough players for a game
            SwingUtilities.invokeLater(() -> {
                quickPlayButton.setBackground(new Color(220, 38, 127)); // Highlight the quick play button
                quickPlayButton.setText("🔥 " + currentPlayerCount + " PLAYERS!");

                // Reset button after 3 seconds
                Timer resetTimer = new Timer(3000, e -> {
                    quickPlayButton.setBackground(new Color(34, 197, 94));
                    quickPlayButton.setText("🚀 QUICK PLAY");
                    ((Timer) e.getSource()).stop();
                });
                resetTimer.start();
            });
        }

        lastPlayerCount = currentPlayerCount;
    }

    private void quickPlayAction() {
        quickPlayButton.setEnabled(false);
        quickPlayButton.setText("Joining...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                try {
                    // Join lobby - this should automatically start game if 2+ players
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
                        // Successfully joined lobby, go to lobby UI
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
                        quickPlayButton.setText("🚀 QUICK PLAY");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ClientUI.this,
                        "Error: " + e.getMessage(),
                        "System Error",
                        JOptionPane.ERROR_MESSAGE);
                    quickPlayButton.setEnabled(true);
                    quickPlayButton.setText("🚀 QUICK PLAY");
                }
            }
        };
        worker.execute();
    }

    private void createLobbyAction() {
        // Create lobby manually (original behavior)
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
        System.out.println("StartClientUI: " + username);
        java.awt.EventQueue.invokeLater(() -> {
            new ClientUI(username).setVisible(true);
        });
    }
}
