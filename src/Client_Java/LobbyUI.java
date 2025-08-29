package Client_Java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static Client_Java.Client.wordyImpl;

public class LobbyUI extends javax.swing.JFrame {
    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }
    static String username;

    // Timer for updating lobby status
    private Timer lobbyUpdateTimer;

    // UI Components for enhanced lobby
    private JLabel countdownLabel;
    private JTextArea playersListArea;
    private JLabel playerCountLabel;
    private JLabel statusLabel;

    public LobbyUI(String username) {
        LobbyUI.username = username;
        initComponents();
        addListeners();
        startLobbyUpdates();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 700));
        setTitle("WordyGame - Lobby");
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(45, 52, 70));
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(31, 41, 55));
        header.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(75, 85, 99), 2, true),
            new EmptyBorder(16, 20, 16, 20)
        ));
        JLabel titleLabel = new JLabel("WAITING FOR PLAYERS TO JOIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        JLabel subLabel = new JLabel("WORDY LOBBY", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        subLabel.setForeground(new Color(168, 162, 158));

        header.add(titleLabel, BorderLayout.CENTER);
        header.add(subLabel, BorderLayout.SOUTH);
        main.add(header, BorderLayout.NORTH);

        // Center area: left (countdown+status), center (art), right (players)
        JPanel center = new JPanel(new BorderLayout(15, 15));
        center.setOpaque(false);

        // Left column
        JPanel leftCol = new JPanel();
        leftCol.setOpaque(false);
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));

        // Countdown panel
        JPanel countdownPanel = new JPanel(new BorderLayout());
        countdownPanel.setBackground(new Color(31, 41, 55));
        countdownPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(239, 68, 68), 2, true),
            new EmptyBorder(12, 16, 12, 16)
        ));
        JLabel countdownTitleLabel = new JLabel("GAME STARTS IN", SwingConstants.CENTER);
        countdownTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        countdownTitleLabel.setForeground(Color.WHITE);
        countdownLabel = new JLabel("--", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        countdownLabel.setForeground(new Color(239, 68, 68));
        countdownPanel.add(countdownTitleLabel, BorderLayout.NORTH);
        countdownPanel.add(countdownLabel, BorderLayout.CENTER);

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBackground(new Color(31, 41, 55));
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(251, 191, 36), 2, true),
            new EmptyBorder(10, 16, 10, 16)
        ));
        statusLabel = new JLabel("Searching for players...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(new Color(251, 191, 36));
        statusPanel.add(statusLabel);

        leftCol.add(countdownPanel);
        leftCol.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCol.add(statusPanel);

        center.add(leftCol, BorderLayout.WEST);

        // Center graphic/info (optional game art placeholder)
        JPanel artPanel = new JPanel(new BorderLayout());
        artPanel.setBackground(new Color(55, 65, 81));
        artPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(107, 114, 128), 2, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        JLabel artTitle = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        artTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        artTitle.setForeground(Color.WHITE);
        JLabel instructions = new JLabel("<html><div style='text-align:center;'>Create the <b>longest word</b> from given letters within the time limit.<br/>Wait here until enough players join, then the game starts automatically.</div></html>", SwingConstants.CENTER);
        instructions.setForeground(new Color(209, 213, 219));
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        artPanel.add(artTitle, BorderLayout.NORTH);
        artPanel.add(instructions, BorderLayout.CENTER);
        center.add(artPanel, BorderLayout.CENTER);

        // Right column - Players panel
        JPanel playersPanel = new JPanel(new BorderLayout(10, 8));
        playersPanel.setBackground(new Color(31, 41, 55));
        playersPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(59, 130, 246), 2, true),
            new EmptyBorder(12, 12, 12, 12)
        ));
        playersPanel.setPreferredSize(new Dimension(300, 0));

        JLabel playersTitle = new JLabel("PLAYERS IN LOBBY", SwingConstants.CENTER);
        playersTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playersTitle.setForeground(Color.WHITE);

        playerCountLabel = new JLabel("Players: 0/5", SwingConstants.CENTER);
        playerCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        playerCountLabel.setForeground(new Color(34, 197, 94));

        JPanel playersHeader = new JPanel(new BorderLayout());
        playersHeader.setOpaque(false);
        playersHeader.add(playersTitle, BorderLayout.NORTH);
        playersHeader.add(playerCountLabel, BorderLayout.SOUTH);

        playersListArea = new JTextArea();
        playersListArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playersListArea.setBackground(new Color(17, 24, 39));
        playersListArea.setForeground(new Color(209, 213, 219));
        playersListArea.setEditable(false);
        playersListArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        playersListArea.setText("Loading players...");
        JScrollPane playersScroll = new JScrollPane(playersListArea);
        playersScroll.setBorder(null);

        playersPanel.add(playersHeader, BorderLayout.NORTH);
        playersPanel.add(playersScroll, BorderLayout.CENTER);

        center.add(playersPanel, BorderLayout.EAST);

        main.add(center, BorderLayout.CENTER);

        // Footer with Exit button
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(new Color(45, 52, 70));
        JButton exitLobbyButton = new JButton("EXIT LOBBY");
        exitLobbyButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitLobbyButton.setForeground(Color.WHITE);
        exitLobbyButton.setBackground(new Color(239, 68, 68));
        exitLobbyButton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 38, 38), 2),
            new EmptyBorder(12, 25, 12, 25)
        ));
        exitLobbyButton.setFocusPainted(false);
        exitLobbyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitLobbyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { exitLobbyButton.setBackground(new Color(220, 38, 38)); }
            public void mouseExited(java.awt.event.MouseEvent evt)  { exitLobbyButton.setBackground(new Color(239, 68, 68)); }
        });
        exitLobbyButton.addActionListener(this::exitLobbyButtonActionPerformed);
        footer.add(exitLobbyButton);
        main.add(footer, BorderLayout.SOUTH);

        setContentPane(main);
        pack();
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
            String playersList = wordyImpl.playerInGameList();
            double playerCount = wordyImpl.lobbyPlayerCount();
            double timer = wordyImpl.gettimer();
            boolean isPlayerInGame = Client.safeIsPlayerInGame(username);

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

            // Determine game state
            if (isPlayerInGame && timer > 0 && timer <= 30) {
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Game Started! Joining...");
                    countdownLabel.setText("LIVE");
                    countdownLabel.setForeground(new Color(34, 197, 94));
                });
                startGameUI(username);
                if (lobbyUpdateTimer != null) {
                    lobbyUpdateTimer.stop();
                }
            } else if (playerCount >= 2 && timer > 0 && timer <= 10) {
                int countdown = (int) timer;
                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setText(String.valueOf(countdown));
                    statusLabel.setText("Game starting soon!");
                    if (countdown <= 3) {
                        countdownLabel.setForeground(new Color(239, 68, 68));
                    } else if (countdown <= 5) {
                        countdownLabel.setForeground(new Color(251, 191, 36));
                    } else {
                        countdownLabel.setForeground(new Color(34, 197, 94));
                    }
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    countdownLabel.setText("--");
                    statusLabel.setText("Waiting for more players...");
                    countdownLabel.setForeground(new Color(168, 162, 158));
                });
            }

        } catch (Exception ex) {
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Connection error");
                playersListArea.setText("Error loading players...");
            });
            System.out.println("Error updating lobby: " + ex.getMessage());
        }
    }

    public void addListeners() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                // Join the server's lobby when the LobbyUI opens
                new Thread(() -> {
                    try {
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText("Joining lobby...");
                        });

                        boolean joined = Client.safeJoinLobby(username);
                        if (joined) {
                            SwingUtilities.invokeLater(() -> {
                                statusLabel.setText("Successfully joined lobby!");
                            });
                            System.out.println("LobbyUI: Successfully joined server lobby for " + username);
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                statusLabel.setText("Failed to join lobby - server may be full or unavailable");
                            });
                            System.out.println("LobbyUI: Failed to join server lobby for " + username);
                        }
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            statusLabel.setText(" Connection error");
                        });
                        System.out.println("LobbyUI: Error joining lobby: " + ex.getMessage());
                    }
                }).start();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Clean up timers and leave lobby
                if (lobbyUpdateTimer != null) {
                    lobbyUpdateTimer.stop();
                }
                try {
                    Client.safeLeaveGame(username);
                    System.out.println("LobbyUI: Left server lobby for " + username);
                } catch (Exception ex) {
                    System.out.println("LobbyUI: Error leaving lobby: " + ex.getMessage());
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
}
