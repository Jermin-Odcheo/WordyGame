package Client_Java;

import static Client_Java.Client.wordyImpl;

public class ClientUI extends javax.swing.JFrame {
    static String username;
    public ClientUI(String username) {
        this.username = username;
        initComponents();
    }

    @SuppressWarnings("unchecked")
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

        backgroundPanel.setBackground(new java.awt.Color(204, 153, 255));

        startGameButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        startGameButton.setForeground(new java.awt.Color(238, 241, 255));
        startGameButton.setText("START GAME");
        startGameButton.setBorder(null);
        startGameButton.setContentAreaFilled(false);
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });

        welcomeMessage.setFont(new java.awt.Font("Segoe UI", 1, 39)); // NOI18N
        welcomeMessage.setForeground(new java.awt.Color(255, 255, 255));
        welcomeMessage.setText("WORD GAME BETA v 3.32.71");

        logo.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png")); // NOI18N

        jLayeredPane2.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane2.setForeground(new java.awt.Color(238, 241, 255));

        backgroundPane.setIcon(new javax.swing.ImageIcon("src/Client_Java/17250835.png")); // NOI18N
        backgroundPane.setText("jLabel2");

        jLayeredPane2.setLayer(backgroundPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
                jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addComponent(backgroundPane, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 37, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
                jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addComponent(backgroundPane, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        leaderboardButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        leaderboardButton.setForeground(new java.awt.Color(238, 241, 255));
        leaderboardButton.setText("LEADERBOARD");
        leaderboardButton.setBorder(null);
        leaderboardButton.setContentAreaFilled(false);
        leaderboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaderboardButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        exitButton.setForeground(new java.awt.Color(238, 241, 255));
        exitButton.setText("EXIT");
        exitButton.setBorder(null);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
                backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGap(46, 46, 46)
                                                .addComponent(welcomeMessage))
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(logo)
                                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                                .addGap(135, 135, 135)
                                                                .addComponent(leaderboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                                .addGap(87, 87, 87)
                                                                .addComponent(startGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                                .addGap(165, 165, 165)
                                                                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        backgroundPanelLayout.setVerticalGroup(
                backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(welcomeMessage)
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(startGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(leaderboardButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(exitButton)
                                                .addGap(161, 161, 161))
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(42, Short.MAX_VALUE))))
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 905, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        wordyImpl.exit(username);
        dispose();
    }

    private void leaderboardButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LeaderboardUI.startLeaderboardUI(username);
    }

    private void startGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
        LobbyUI.startLobby(username);
        dispose();
    }


    public static void startClientUI(String username) {
        System.out.println("StartClientUI: " + username);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientUI(username).setVisible(true);
            }
        });
    }

    private javax.swing.JLabel backgroundPane;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLabel logo;
    private javax.swing.JButton leaderboardButton;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLabel welcomeMessage;

}

