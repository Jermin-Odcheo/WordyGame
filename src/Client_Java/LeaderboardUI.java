package Client_Java;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LeaderboardUI extends JFrame {
    private static final Color BG_COLOR     = new Color(204, 153, 255);
    private static final Color BTN_COLOR    = new Color(57,  72, 103);
    private static final Color BTN_FG       = new Color(241, 246, 249);
    private static final Color BTN_HOVER    = BTN_COLOR.brighter();
    private final String username;
    private JPanel  backgroundPanel;
    private JLabel  leaderboardLogo;
    private JButton longestWordsButton;
    private JButton topWinsButton;

    public LeaderboardUI(String username) {
        super("Leaderboard");
        this.username = username;
        // Window settings
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // --- Background panel ---
        backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.setBackground(BG_COLOR);
        backgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- Logo ---
        leaderboardLogo = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        leaderboardLogo.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 28));
        leaderboardLogo.setForeground(Color.WHITE);
        backgroundPanel.add(leaderboardLogo, BorderLayout.NORTH);

        // --- Buttons ---
        longestWordsButton = makeStyledButton("LONGEST WORDS");
        topWinsButton      = makeStyledButton("TOP WINS");

        longestWordsButton.addActionListener(e -> LongestWordsUI.startLongestWordsUI(username));
        topWinsButton     .addActionListener(e -> TopWinsUI.startTopWinsUI(username));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(longestWordsButton);
        buttonsPanel.add(topWinsButton);

        backgroundPanel.add(buttonsPanel, BorderLayout.CENTER);

        // --- Finalize ---
        setContentPane(backgroundPanel);
    }

    private JButton makeStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
        btn.setBackground(BTN_COLOR);
        btn.setForeground(BTN_FG);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 60));
        // rounded border
        btn.setBorder(new CompoundBorder(
                new RoundedBorder(15),
                new EmptyBorder(5, 15, 5, 15)
        ));
        // hover effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BTN_HOVER); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(BTN_COLOR); }
        });
        return btn;
    }

    // simple rounded-corner border
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;
        RoundedBorder(int radius) { this.radius = radius; }

        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
    }

    public static void startLeaderboardUI(String username) {
        // set Nimbus if available
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        } catch (Exception ex) { /* ignore */ }

        SwingUtilities.invokeLater(() -> {
            new LeaderboardUI(username).setVisible(true);
        });
    }
}
