package Client_Java;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LeaderboardUI extends JFrame {
    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }

    private static final Color BG_DARK    = new Color(45, 52, 70);
    private static final Color PANEL_DARK = new Color(31, 41, 55);
    private static final Color ACCENT     = new Color(59, 130, 246);
    private static final Color BTN_COLOR  = new Color(59, 130, 246);
    private static final Color BTN_FG     = Color.WHITE;
    private static final Color BTN_HOVER  = new Color(99, 160, 255);

    private final String username;
    private JLabel  leaderboardLogo;
    private JButton longestWordsButton;
    private JButton topWinsButton;

    public LeaderboardUI(String username) {
        super("WordyGame - Leaderboard");
        this.username = username;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 380);
        setResizable(true);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(BG_DARK);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL_DARK);
        header.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(75, 85, 99), 2, true),
            new EmptyBorder(16, 20, 16, 20)
        ));
        leaderboardLogo = new JLabel("LEADERBOARD", SwingConstants.CENTER);
        leaderboardLogo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        leaderboardLogo.setForeground(Color.WHITE);
        header.add(leaderboardLogo, BorderLayout.CENTER);
        main.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        center.setBackground(BG_DARK);

        longestWordsButton = makeStyledButton("Longest Words");
        topWinsButton      = makeStyledButton("Top Wins");

        longestWordsButton.addActionListener(e -> LongestWordsUI.startLongestWordsUI(username));
        topWinsButton     .addActionListener(e -> TopWinsUI.startTopWinsUI(username));

        center.add(longestWordsButton);
        center.add(topWinsButton);
        main.add(center, BorderLayout.CENTER);

        setContentPane(main);
    }

    private JButton makeStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(BTN_COLOR);
        btn.setForeground(BTN_FG);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(220, 54));
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACCENT.darker(), 2, true),
            new EmptyBorder(10, 18, 10, 18)
        ));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BTN_HOVER); }
            public void mouseExited (MouseEvent e) { btn.setBackground(BTN_COLOR); }
        });
        return btn;
    }

    public static void startLeaderboardUI(String username) {
        SwingUtilities.invokeLater(() -> new LeaderboardUI(username).setVisible(true));
    }
}
