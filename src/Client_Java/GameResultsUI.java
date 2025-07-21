package Client_Java;

import Client_Java.corbaGame.getRoundWin;
import Client_Java.corbaGame.getWin;
import Client_Java.corbaGame.isSameLength;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Client_Java.Client.wordyImpl;

public class GameResultsUI extends JFrame {
    private static String username;
    private Timer playAgainUpdateTimer;

    // UI Components
    private JLabel resultsTitleLabel;
    private JLabel winnerLabel;
    private JTextArea wordSubmissionsArea;
    private JTextArea playAgainPlayersArea;
    private JButton playAgainButton;
    private JButton backToLobbyButton;
    private JLabel playAgainCountLabel;

    // Game results data
    private String gameResults;
    private String allPlayerWords;
    private List<String> playAgainPlayers;
    private boolean hasVotedPlayAgain = false;

    public GameResultsUI(String username) {
        GameResultsUI.username = username;
        this.playAgainPlayers = new ArrayList<>();
        initializeUI();
        addListeners();
        loadGameResults();
        startPlayAgainMonitoring();
    }

    private void initializeUI() {
        setTitle("WordyGame - Game Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(45, 52, 70));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        createHeaderPanel(mainPanel);
        createResultsPanel(mainPanel);
        createPlayAgainPanel(mainPanel);
        createButtonPanel(mainPanel);

        add(mainPanel);
    }

    private void createHeaderPanel(JPanel mainPanel) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(31, 41, 55));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(75, 85, 99), 2, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        resultsTitleLabel = new JLabel("GAME RESULTS", SwingConstants.CENTER);
        resultsTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        resultsTitleLabel.setForeground(Color.WHITE);

        winnerLabel = new JLabel("Loading results...", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        winnerLabel.setForeground(new Color(251, 191, 36));

        headerPanel.add(resultsTitleLabel, BorderLayout.NORTH);
        headerPanel.add(winnerLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    private void createResultsPanel(JPanel mainPanel) {
        JPanel resultsPanel = new JPanel(new BorderLayout(10, 10));
        resultsPanel.setBackground(new Color(55, 65, 81));
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(107, 114, 128), 2, true),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel submissionsLabel = new JLabel("Word Submissions:");
        submissionsLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        submissionsLabel.setForeground(Color.WHITE);

        wordSubmissionsArea = new JTextArea();
        wordSubmissionsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        wordSubmissionsArea.setBackground(new Color(17, 24, 39));
        wordSubmissionsArea.setForeground(new Color(209, 213, 219));
        wordSubmissionsArea.setEditable(false);
        wordSubmissionsArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        wordSubmissionsArea.setText("Loading word submissions...");
        wordSubmissionsArea.setLineWrap(true);
        wordSubmissionsArea.setWrapStyleWord(true);

        JScrollPane submissionsScroll = new JScrollPane(wordSubmissionsArea);
        submissionsScroll.setBorder(null);
        submissionsScroll.setPreferredSize(new Dimension(0, 200));

        resultsPanel.add(submissionsLabel, BorderLayout.NORTH);
        resultsPanel.add(submissionsScroll, BorderLayout.CENTER);

        mainPanel.add(resultsPanel, BorderLayout.CENTER);
    }

    private void createPlayAgainPanel(JPanel mainPanel) {
        JPanel playAgainPanel = new JPanel(new BorderLayout(10, 10));
        playAgainPanel.setBackground(new Color(55, 65, 81));
        playAgainPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(59, 130, 246), 2, true),
                new EmptyBorder(15, 15, 15, 15)
        ));
        playAgainPanel.setPreferredSize(new Dimension(0, 180));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(55, 65, 81));

        JLabel playAgainLabel = new JLabel("Players Wanting to Play Again:");
        playAgainLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        playAgainLabel.setForeground(Color.WHITE);

        playAgainCountLabel = new JLabel("0 players ready", SwingConstants.CENTER);
        playAgainCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        playAgainCountLabel.setForeground(new Color(34, 197, 94));

        headerPanel.add(playAgainLabel, BorderLayout.WEST);
        headerPanel.add(playAgainCountLabel, BorderLayout.EAST);

        playAgainPlayersArea = new JTextArea();
        playAgainPlayersArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playAgainPlayersArea.setBackground(new Color(17, 24, 39));
        playAgainPlayersArea.setForeground(new Color(209, 213, 219));
        playAgainPlayersArea.setEditable(false);
        playAgainPlayersArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        playAgainPlayersArea.setText("No players have voted to play again yet...");

        JScrollPane playAgainScroll = new JScrollPane(playAgainPlayersArea);
        playAgainScroll.setBorder(null);

        playAgainPanel.add(headerPanel, BorderLayout.NORTH);
        playAgainPanel.add(playAgainScroll, BorderLayout.CENTER);

        mainPanel.add(playAgainPanel, BorderLayout.EAST);
        playAgainPanel.setPreferredSize(new Dimension(300, 0));
    }

    private void createButtonPanel(JPanel mainPanel) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(45, 52, 70));
        buttonPanel.setPreferredSize(new Dimension(0, 80));

        playAgainButton = createStyledButton("Play Again", new Color(34, 197, 94));
        backToLobbyButton = createStyledButton("Back to Main Menu", new Color(107, 114, 128));

        playAgainButton.addActionListener(e -> votePlayAgain());
        backToLobbyButton.addActionListener(e -> backToMainMenu());

        buttonPanel.add(playAgainButton);
        buttonPanel.add(backToLobbyButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(color.darker(), 2),
                new EmptyBorder(12, 25, 12, 25)
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

    private void loadGameResults() {
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                String validWords = null;
                for (int attempt = 0; attempt < 3; attempt++) {
                    validWords = wordyImpl.getValidWordFromClients();
                    if (validWords != null && !validWords.trim().isEmpty()) break;
                    Thread.sleep(500);
                }

                String winnerResult;
                try {
                    wordyImpl.getWinner();
                    winnerResult = "NO_CLEAR_RESULT";
                } catch (getWin win) {
                    winnerResult = "WINNER:" + win.message;
                } catch (isSameLength tie) {
                    winnerResult = "TIE:" + tie.message;
                } catch (getRoundWin roundWin) {
                    winnerResult = "WINNER:" + roundWin.message;
                }

                gameResults = winnerResult;
                allPlayerWords = validWords;

                SwingUtilities.invokeLater(() -> {
                    displayGameResults();
                    displayWordSubmissions();
                });

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    winnerLabel.setText("Error: " + e.getMessage());
                    winnerLabel.setForeground(Color.RED);
                    wordSubmissionsArea.setText("Error loading submissions: " + e.getMessage());
                });
            }
        }).start();
    }

    private void displayGameResults() {
        if (gameResults == null || gameResults.isEmpty()) {
            winnerLabel.setText("No results available");
            return;
        }
        if (gameResults.equals("NO_SUBMISSIONS")) {
            winnerLabel.setText("No words submitted - It's a tie!");
            winnerLabel.setForeground(new Color(168, 162, 158));
        } else if (gameResults.startsWith("WINNER:")) {
            String winner = gameResults.substring(gameResults.indexOf(':', 7) + 1).trim();
            winnerLabel.setText("Winner: " + winner);
            winnerLabel.setForeground(new Color(251, 191, 36));
        } else if (gameResults.startsWith("TIE:")) {
            String tieInfo = gameResults.substring(4).trim();
            winnerLabel.setText(tieInfo);
            winnerLabel.setForeground(new Color(59, 130, 246));
        } else {
            winnerLabel.setText(gameResults);
            winnerLabel.setForeground(new Color(168, 162, 158));
        }
    }

    private void displayWordSubmissions() {
        if (allPlayerWords == null || allPlayerWords.trim().isEmpty()) {
            wordSubmissionsArea.setText("No word submission details available.");
            return;
        }
        StringBuilder sb = new StringBuilder("Word submissions this game:\n\n");
        String[] lines = allPlayerWords.contains(";")
                ? allPlayerWords.split(";")
                : allPlayerWords.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(":");
            if (parts.length >= 2) {
                String player = parts[0].trim();
                String word = parts[1].trim();
                String length = parts.length >= 3 ? parts[2].trim() : String.valueOf(word.length());
                sb.append("• ").append(player).append(": \"")
                        .append(word).append("\" (").append(length).append(" letters)\n");
            }
        }
        wordSubmissionsArea.setText(sb.toString());
    }

    private void votePlayAgain() {
        if (hasVotedPlayAgain) {
            showMessage("You have already voted!", new Color(251, 191, 36));
            return;
        }
        try {
            wordyImpl.votePlayAgain(username);
            hasVotedPlayAgain = true;
            playAgainButton.setText("Voted to Play Again");
            playAgainButton.setEnabled(false);
            showMessage("Voted! Waiting for others...", new Color(34, 197, 94));
        } catch (Exception ex) {
            showMessage("Error voting: " + ex.getMessage(), Color.RED);
        }
    }

    private void startPlayAgainMonitoring() {
        playAgainUpdateTimer = new Timer(2000, e -> {
            try {
                String[] votes = wordyImpl.getPlayAgainList();
                List<String> newList = Arrays.asList(votes);
                if (!newList.equals(playAgainPlayers)) {
                    playAgainPlayers = new ArrayList<>(newList);
                    SwingUtilities.invokeLater(() -> {
                        updatePlayAgainDisplay();
                        if (hasVotedPlayAgain && playAgainPlayers.size() >= 2) {
                            transitionToNewGame();
                        }
                    });
                }
            } catch (Exception ex) {
                System.out.println("PlayAgain monitor error: " + ex.getMessage());
            }
        });
        playAgainUpdateTimer.start();
    }

    private void updatePlayAgainDisplay() {
        playAgainCountLabel.setText(playAgainPlayers.size() + " players ready");
        if (playAgainPlayers.isEmpty()) {
            playAgainPlayersArea.setText("No votes yet...");
            playAgainCountLabel.setForeground(new Color(168, 162, 158));
        } else {
            playAgainPlayersArea.setText(String.join("\n", playAgainPlayers));
            playAgainCountLabel.setForeground(new Color(34, 197, 94));
        }
    }

    private void transitionToNewGame() {
        playAgainUpdateTimer.stop();
        showMessage("Starting new game!", new Color(34, 197, 94));
        new Timer(2000, evt -> {
            ((Timer) evt.getSource()).stop();
            dispose();
            ClientUI.startClientUI(username);
        }).start();
    }

    private void backToMainMenu() {
        if (playAgainUpdateTimer != null) playAgainUpdateTimer.stop();
        dispose();
        ClientUI.startClientUI(username);
    }

    private void showMessage(String message, Color color) {
        JLabel msg = new JLabel(message, SwingConstants.CENTER);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        msg.setForeground(color);
        msg.setOpaque(true);
        msg.setBackground(new Color(45, 52, 70));
        msg.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel content = (JPanel) getContentPane();
        content.add(msg, BorderLayout.NORTH);
        content.revalidate();
        content.repaint();

        new Timer(3000, e -> {
            content.remove(msg);
            content.revalidate();
            content.repaint();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    public void addListeners() {
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (playAgainUpdateTimer != null) playAgainUpdateTimer.stop();
                try {
                    Client.safeLeaveGame(username);
                } catch (Exception ex) {
                    System.out.println("LeaveGame error: " + ex.getMessage());
                }
            }
            @Override public void windowOpened(WindowEvent e) {}
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
    }

    public static void startGameResults(String username) {
        SwingUtilities.invokeLater(() -> new GameResultsUI(username).setVisible(true));
    }
}
