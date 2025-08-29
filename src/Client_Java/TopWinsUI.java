package Client_Java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;

import static Client_Java.Client.wordyImpl;

public class TopWinsUI extends javax.swing.JFrame {

    private String username;
    private JTable table;

    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }

    public TopWinsUI(String username) {
        this.username = username;
        initComponents();
        displayWinCount();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("WordyGame - Top Wins");
        setResizable(true);
        setSize(640, 420);
        setLocationRelativeTo(null);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(45, 52, 70));
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(31, 41, 55));
        header.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(75, 85, 99), 2, true),
            new EmptyBorder(14, 18, 14, 18)
        ));
        JLabel title = new JLabel("TOP WINS", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        main.add(header, BorderLayout.NORTH);

        // Table
        String[] cols = {"Top", "Player", "Wins"};
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(55, 65, 81));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(new Color(17, 24, 39));
        table.setForeground(new Color(209, 213, 219));
        table.setGridColor(new Color(75, 85, 99));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(new Color(107, 114, 128), 1, true));
        main.add(scroll, BorderLayout.CENTER);

        setContentPane(main);
    }

    private void displayWinCount() {
        String[] wordDataList = wordyImpl.displayWinsList();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table

        int rank = 1;
        for (String winData : wordDataList) {
            String[] data = winData.split(",");
            if (data.length >= 2) {
                String user = data[0];
                String wins = data[1].toUpperCase();
                model.addRow(new Object[]{rank++, user, wins});
            }
        }
    }

    public static void startTopWinsUI(String username) {
        SwingUtilities.invokeLater(() -> new TopWinsUI(username).setVisible(true));
    }
}
