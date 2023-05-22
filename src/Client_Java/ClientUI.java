package Client_Java;

import static Client_Java.Client.wordyImpl;

public class ClientUI extends javax.swing.JFrame {
    private javax.swing.JLabel backgroundImage;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLabel logo;
    private javax.swing.JButton leaderboardButton;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLabel welcomeMessage;
    static String username;
    public ClientUI(String username) {
        this.username = username;
        initComponents();
    }
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        startGameButton = new javax.swing.JButton();
        leaderboardButton = new javax.swing.JButton();
        welcomeMessage = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        backgroundImage = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 550));
        getContentPane().setLayout(null);

        backgroundPanel.setBackground(new java.awt.Color(33, 42, 62));

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

        welcomeMessage.setFont(new java.awt.Font("Segoe UI", 1, 39)); // NOI18N
        welcomeMessage.setForeground(new java.awt.Color(255, 255, 255));
        welcomeMessage.setText("WORD GAME BETA v 3.32.71");

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/clientui/finalclientui/logo.png"))); // NOI18N

        backgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/clientui/finalclientui/background.jpg"))); // NOI18N

        jLayeredPane2.setLayer(backgroundImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
                jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 905, Short.MAX_VALUE)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(backgroundImage, javax.swing.GroupLayout.PREFERRED_SIZE, 905, Short.MAX_VALUE))
        );
        jLayeredPane2Layout.setVerticalGroup(
                jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 524, Short.MAX_VALUE)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(backgroundImage, javax.swing.GroupLayout.PREFERRED_SIZE, 524, Short.MAX_VALUE))
        );

        exitButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setText("EXIT");
        exitButton.setBorder(null);
        exitButton.setBorderPainted(false);
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
                                                                .addGap(149, 149, 149)
                                                                .addComponent(leaderboardButton))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(41, 41, 41))))
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGap(567, 567, 567)
                                                .addComponent(startGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(75, 75, 75))
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane2))
        );
        backgroundPanelLayout.setVerticalGroup(
                backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(welcomeMessage)
                                .addGap(26, 26, 26)
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                                .addGap(190, 190, 190)
                                                                .addComponent(leaderboardButton))
                                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                                .addGap(141, 141, 141)
                                                                .addComponent(startGameButton)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(exitButton)))
                                .addGap(42, 42, 42))
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane2))
        );

        getContentPane().add(backgroundPanel);
        backgroundPanel.setBounds(0, 0, 905, 524);

        pack();
    }// </editor-fold>
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


}

