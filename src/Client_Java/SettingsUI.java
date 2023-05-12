package Client_Java;

public class SettingsUI extends javax.swing.JFrame {

    public SettingsUI() {
        initComponents();
    }
    private void initComponents() {

        settingsLabel = new javax.swing.JLabel();
        changeBackgroundLabel = new javax.swing.JLabel();
        gradientBackgroundButton = new javax.swing.JButton();
        redPatternBackgroundButton = new javax.swing.JButton();
        greenPatternBackgroundButton = new javax.swing.JButton();
        viewLeaderboardLabel = new javax.swing.JLabel();
        top5LongestWordButton = new javax.swing.JButton();
        topWinsButton = new javax.swing.JButton();
        backgroundPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(755, 550));
        getContentPane().setLayout(null);

        settingsLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        settingsLabel.setForeground(new java.awt.Color(255, 255, 255));
        settingsLabel.setText("SETTINGS");
        getContentPane().add(settingsLabel);
        settingsLabel.setBounds(270, 20, 200, 40);

        changeBackgroundLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        changeBackgroundLabel.setForeground(new java.awt.Color(255, 255, 255));
        changeBackgroundLabel.setText("CHANGE BACKGROUND");
        getContentPane().add(changeBackgroundLabel);
        changeBackgroundLabel.setBounds(30, 80, 240, 30);

        gradientBackgroundButton.setBackground(new java.awt.Color(153, 0, 204));
        gradientBackgroundButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        gradientBackgroundButton.setForeground(new java.awt.Color(255, 255, 255));
        gradientBackgroundButton.setText("GRADIENT");
        gradientBackgroundButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gradientBackgroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradientBackgroundButtonActionPerformed(evt);
            }
        });
        getContentPane().add(gradientBackgroundButton);
        gradientBackgroundButton.setBounds(40, 130, 190, 60);

        redPatternBackgroundButton.setBackground(new java.awt.Color(255, 102, 153));
        redPatternBackgroundButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        redPatternBackgroundButton.setForeground(new java.awt.Color(255, 255, 255));
        redPatternBackgroundButton.setText("RED PATTERN");
        redPatternBackgroundButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        redPatternBackgroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redPatternBackgroundAButtonActionPerformed(evt);
            }
        });
        getContentPane().add(redPatternBackgroundButton);
        redPatternBackgroundButton.setBounds(260, 130, 200, 60);

        greenPatternBackgroundButton.setBackground(new java.awt.Color(0, 153, 153));
        greenPatternBackgroundButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        greenPatternBackgroundButton.setForeground(new java.awt.Color(255, 255, 255));
        greenPatternBackgroundButton.setText("GREEN PATTERN");
        greenPatternBackgroundButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        greenPatternBackgroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                greenPatternBackgroundButtonActionPerformed(evt);
            }
        });
        getContentPane().add(greenPatternBackgroundButton);
        greenPatternBackgroundButton.setBounds(490, 130, 210, 60);

        viewLeaderboardLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        viewLeaderboardLabel.setForeground(new java.awt.Color(255, 255, 255));
        viewLeaderboardLabel.setText("VIEW LEADERBOARD");
        getContentPane().add(viewLeaderboardLabel);
        viewLeaderboardLabel.setBounds(30, 260, 220, 21);

        top5LongestWordButton.setBackground(new java.awt.Color(29, 38, 125));
        top5LongestWordButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        top5LongestWordButton.setForeground(new java.awt.Color(255, 255, 255));
        top5LongestWordButton.setText("TOP 5 LONGEST WORD");
        top5LongestWordButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        top5LongestWordButton.setPreferredSize(new java.awt.Dimension(1, 1));
        top5LongestWordButton.setVerifyInputWhenFocusTarget(false);
        top5LongestWordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                top5LongestWordButtonActionPerformed(evt);
            }
        });
        getContentPane().add(top5LongestWordButton);
        top5LongestWordButton.setBounds(240, 300, 260, 60);

        topWinsButton.setBackground(new java.awt.Color(92, 70, 156));
        topWinsButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        topWinsButton.setForeground(new java.awt.Color(255, 255, 255));
        topWinsButton.setText("TOP WINS");
        topWinsButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topWinsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topWinsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(topWinsButton);
        topWinsButton.setBounds(270, 370, 190, 60);

        backgroundPanel.setBackground(new java.awt.Color(12, 19, 79));

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
                backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 740, Short.MAX_VALUE)
        );
        backgroundPanelLayout.setVerticalGroup(
                backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 530, Short.MAX_VALUE)
        );

        getContentPane().add(backgroundPanel);
        backgroundPanel.setBounds(0, 0, 740, 530);

        pack();
    }

    private void gradientBackgroundButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void redPatternBackgroundAButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    private void greenPatternBackgroundButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void top5LongestWordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void topWinsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsUI().setVisible(true);
            }
        });
    }

    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel changeBackgroundLabel;
    private javax.swing.JButton gradientBackgroundButton;
    private javax.swing.JButton greenPatternBackgroundButton;
    private javax.swing.JButton redPatternBackgroundButton;
    private javax.swing.JLabel settingsLabel;
    private javax.swing.JButton top5LongestWordButton;
    private javax.swing.JButton topWinsButton;
    private javax.swing.JLabel viewLeaderboardLabel;
}
