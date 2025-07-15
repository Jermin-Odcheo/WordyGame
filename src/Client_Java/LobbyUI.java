package Client_Java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static Client_Java.Client.wordyImpl;

public class LobbyUI extends javax.swing.JFrame {
    static String username;

    // Timer for updating lobby status
    private Timer lobbyUpdateTimer;
    private Timer countdownTimer;

    // UI Components for enhanced lobby
    private JLabel countdownLabel;
    private JTextArea playersListArea;
    private JLabel playerCountLabel;
    private JLabel statusLabel;

    public LobbyUI(String username) {
        this.username = username;
        initComponents();
        addListeners();
        startLobbyUpdates();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1270, 750));
        setTitle("WordyGame - Lobby");
        setLocationRelativeTo(null);
        setResizable(false);

        // Set dark background
        getContentPane().setBackground(new Color(45, 52, 70));
        getContentPane().setLayout(null);

        // Main title
        JLabel titleLabel = new JLabel("WAITING FOR PLAYERS TO JOIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(200, 30, 870, 50);
        getContentPane().add(titleLabel);

        // Lobby info
        JLabel lobbyLabel = new JLabel("WORDY LOBBY", SwingConstants.CENTER);
        lobbyLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lobbyLabel.setForeground(new Color(168, 162, 158));
        lobbyLabel.setBounds(200, 85, 870, 30);
        getContentPane().add(lobbyLabel);

        // Countdown section
        createCountdownSection();

        // Players section
        createPlayersSection();

        // Status section
        createStatusSection();

        // Game icon (center)
        JLabel gameIcon = new JLabel();
        gameIcon.setIcon(new ImageIcon("src/Server_Java/WORD.png"));
        gameIcon.setBounds(430, 200, 438, 300);
        getContentPane().add(gameIcon);

        // Instructions
        createInstructionsSection();

        // Exit button
        createExitButton();

        // Background
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("src/Client_Java/17250835.png"));
        backgroundLabel.setBounds(0, 0, 1270, 750);
        getContentPane().add(backgroundLabel);
    }

    private void createCountdownSection() {
        // Countdown panel
        JPanel countdownPanel = new JPanel(new BorderLayout());
        countdownPanel.setBackground(new Color(31, 41, 55));
        countdownPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(239, 68, 68), 3, true),
            new EmptyBorder(15, 20, 15, 20)
        ));
        countdownPanel.setBounds(50, 130, 300, 120);

        JLabel countdownTitleLabel = new JLabel("⏱️ GAME STARTS IN", SwingConstants.CENTER);
        countdownTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        countdownTitleLabel.setForeground(Color.WHITE);

        countdownLabel = new JLabel("--", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        countdownLabel.setForeground(new Color(239, 68, 68));

        countdownPanel.add(countdownTitleLabel, BorderLayout.NORTH);
        countdownPanel.add(countdownLabel, BorderLayout.CENTER);

        getContentPane().add(countdownPanel);
    }

    private void createPlayersSection() {
        // Players panel
        JPanel playersPanel = new JPanel(new BorderLayout(10, 10));
        playersPanel.setBackground(new Color(31, 41, 55));
        playersPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(59, 130, 246), 3, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        playersPanel.setBounds(920, 130, 300, 350);

        JLabel playersTitle = new JLabel("👥 PLAYERS IN LOBBY", SwingConstants.CENTER);
        playersTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playersTitle.setForeground(Color.WHITE);

        playerCountLabel = new JLabel("Players: 0/5", SwingConstants.CENTER);
        playerCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        playerCountLabel.setForeground(new Color(34, 197, 94));

        playersListArea = new JTextArea();
        playersListArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        playersListArea.setBackground(new Color(17, 24, 39));
        playersListArea.setForeground(new Color(209, 213, 219));
        playersListArea.setEditable(false);
        playersListArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        playersListArea.setText("Loading players...");

        JScrollPane playersScroll = new JScrollPane(playersListArea);
        playersScroll.setBorder(null);
        playersScroll.setPreferredSize(new Dimension(0, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(31, 41, 55));
        headerPanel.add(playersTitle, BorderLayout.NORTH);
        headerPanel.add(playerCountLabel, BorderLayout.SOUTH);

        playersPanel.add(headerPanel, BorderLayout.NORTH);
        playersPanel.add(playersScroll, BorderLayout.CENTER);

        getContentPane().add(playersPanel);
    }

    private void createStatusSection() {
        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBackground(new Color(31, 41, 55));
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(251, 191, 36), 2, true),
            new EmptyBorder(10, 20, 10, 20)
        ));
        statusPanel.setBounds(50, 270, 300, 80);

        statusLabel = new JLabel("🔍 Searching for players...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(new Color(251, 191, 36));

        statusPanel.add(statusLabel);
        getContentPane().add(statusPanel);
    }

    private void createInstructionsSection() {
        JPanel instructionsPanel = new JPanel(new BorderLayout(10, 10));
        instructionsPanel.setBackground(new Color(55, 65, 81));
        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(107, 114, 128), 2, true),
            new EmptyBorder(15, 20, 15, 20)
        ));
        instructionsPanel.setBounds(200, 520, 870, 100);

        JLabel howToPlayLabel = new JLabel("🎮 HOW TO PLAY:", SwingConstants.LEFT);
        howToPlayLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        howToPlayLabel.setForeground(Color.WHITE);

        JLabel instructionsLabel = new JLabel("<html>Create the <b>longest word</b> possible from the given letters within the time limit.<br/>Click letter tiles to build words and compete with other players for the highest score!</html>");
        instructionsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionsLabel.setForeground(new Color(209, 213, 219));

        instructionsPanel.add(howToPlayLabel, BorderLayout.NORTH);
        instructionsPanel.add(instructionsLabel, BorderLayout.CENTER);

        getContentPane().add(instructionsPanel);
    }

    private void createExitButton() {
        JButton exitLobbyButton = new JButton("🚪 EXIT LOBBY");
        exitLobbyButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitLobbyButton.setForeground(Color.WHITE);
        exitLobbyButton.setBackground(new Color(239, 68, 68));
        exitLobbyButton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 38, 38), 2),
            new EmptyBorder(12, 25, 12, 25)
        ));
        exitLobbyButton.setFocusPainted(false);
        exitLobbyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitLobbyButton.setBounds(560, 650, 160, 50);

        // Hover effect
        exitLobbyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitLobbyButton.setBackground(new Color(220, 38, 38));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitLobbyButton.setBackground(new Color(239, 68, 68));
            }
        });

        exitLobbyButton.addActionListener(this::exitLobbyButtonActionPerformed);
        getContentPane().add(exitLobbyButton);
    }

    private void startLobbyUpdates() {
        // Update lobby status every 1 second
        lobbyUpdateTimer = new Timer(1000, e -> updateLobbyStatus());
        lobbyUpdateTimer.start();

        // Initial update
        updateLobbyStatus();
    }

    private void updateLobbyStatus() {
        try {
            // Get current players in lobby
            String playersList = wordyImpl.playerInGameList();
            double playerCount = wordyImpl.lobbyPlayerCount();
            double countdown = wordyImpl.gettimer();

            // Update players list
            if (playersList != null && !playersList.isEmpty() && !playersList.equals("[]")) {
                String formattedList = playersList.replace(",", "\n")
                    .replace("[", "").replace("]", "").trim();
                SwingUtilities.invokeLater(() -> {
                    playersListArea.setText(formattedList);
                    playerCountLabel.setText("Players: " + (int)playerCount + "/5");
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    playersListArea.setText("No players in lobby yet...");
                    playerCountLabel.setText("Players: 0/5");
                });
            }

            // Update countdown
            if (playerCount >= 2 && countdown > 0) {
                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setText(String.valueOf((int)countdown));
                    statusLabel.setText("🚀 Game starting soon!");

                    // Change color based on countdown
                    if (countdown <= 3) {
                        countdownLabel.setForeground(new Color(239, 68, 68)); // Red
                    } else if (countdown <= 5) {
                        countdownLabel.setForeground(new Color(251, 191, 36)); // Yellow
                    } else {
                        countdownLabel.setForeground(new Color(34, 197, 94)); // Green
                    }
                });
            } else if (playerCount >= 2) {
                // Game should be starting
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("🎮 Starting game...");
                    countdownLabel.setText("GO!");
                });

                // Start game after short delay
                Timer startGameTimer = new Timer(2000, startEvent -> {
                    startGameUI(username);
                    if (lobbyUpdateTimer != null) {
                        lobbyUpdateTimer.stop();
                    }
                });
                startGameTimer.setRepeats(false);
                startGameTimer.start();

            } else {
                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setText("--");
                    statusLabel.setText("🔍 Waiting for more players...");
                    countdownLabel.setForeground(new Color(168, 162, 158)); // Gray
                });
            }

        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("❌ Connection error");
                playersListArea.setText("Error loading players...");
            });
            System.out.println("Error updating lobby: " + ex.getMessage());
        }
    }

    public void addListeners() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    // Join the lobby when window opens
                    boolean joined = wordyImpl.joinLobby(username);
                    if (!joined) {
                        statusLabel.setText("❌ Failed to join lobby");
                    }
                } catch (Exception ex) {
                    statusLabel.setText("❌ Connection error");
                    System.out.println("Error joining lobby: " + ex.getMessage());
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Clean up timers and leave lobby
                if (lobbyUpdateTimer != null) {
                    lobbyUpdateTimer.stop();
                }
                try {
                    wordyImpl.leaveGame(username);
                } catch (Exception ex) {
                    System.out.println("Error leaving lobby: " + ex.getMessage());
                }
            }

            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
    }

    private void startGameUI(String username) {
        SwingUtilities.invokeLater(() -> {
            this.dispose();
            GameUI.startGameUI(username);
        });
    }

    private void exitLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Stop timers
            if (lobbyUpdateTimer != null) {
                lobbyUpdateTimer.stop();
            }

            // Leave the game/lobby
            wordyImpl.leaveGame(username);

            // Close lobby and return to client UI
            dispose();
            ClientUI.startClientUI(username);

        } catch (Exception e) {
            System.out.println("Error leaving lobby: " + e.getMessage());
            dispose();
            ClientUI.startClientUI(username);
        }
    }

    public static void startLobby(String username) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LobbyUI(username).setVisible(true));
    }

    // Legacy variables for compatibility (if needed)
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel matchTimerField;
    private javax.swing.JLabel playerCountField;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton exitLobbyButton;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel jLabel7;
}
