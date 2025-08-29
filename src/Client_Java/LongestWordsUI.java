package Client_Java;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;

import static Client_Java.Client.wordyImpl;

public class LongestWordsUI extends javax.swing.JFrame {

    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }

    static String username;
    private JTable table;

    public LongestWordsUI(String username) {
        LongestWordsUI.username = username;
        initComponents();
        displayWordList();
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("WordyGame - Longest Words");
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
        JLabel title = new JLabel("Longest Words", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.CENTER);
        main.add(header, BorderLayout.NORTH);

        // Table
        String[] cols = {"User ID", "Word"};
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

    private void displayWordList() {
        String[] wordDataList = wordyImpl.displayWordList();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table

        for (String wordData : wordDataList) {
            String[] data = wordData.split(",");
            if (data.length >= 2) {
                String user = data[0];
                String word = data[1].toUpperCase();
                model.addRow(new Object[]{user, word});
            }
        }
    }


    public static void startLongestWordsUI(String username) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LongestWordsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LongestWordsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LongestWordsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LongestWordsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LongestWordsUI(username).setVisible(true);
            }
        });
    }

}
