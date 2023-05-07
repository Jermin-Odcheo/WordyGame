package Client_Java;

public class LobbyUI extends javax.swing.JFrame {

    public LobbyUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        logo = new javax.swing.JLabel();
        waitingText = new javax.swing.JLabel();
        countdownText = new javax.swing.JLabel();
        countdownTextField = new javax.swing.JTextField();
        playersInLobbyText = new javax.swing.JLabel();
        playerCountTextField = new javax.swing.JTextField();
        instructionText = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("src/Client_Java/17250835.png"))); // NOI18N

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("src/Server_Java/WORD.png"))); // NOI18N

        waitingText.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        waitingText.setForeground(new java.awt.Color(255, 235, 246));
        waitingText.setText("WAITING FOR PLAYERS TO JOIN ");

        countdownText.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        countdownText.setForeground(new java.awt.Color(255, 235, 246));
        countdownText.setText("MATCH WILL START IN");

        countdownTextField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        countdownTextField.setForeground(new java.awt.Color(255, 235, 246));
        countdownTextField.setText("10");
        countdownTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countdownTextFieldActionPerformed(evt);
            }
        });

        playersInLobbyText.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        playersInLobbyText.setForeground(new java.awt.Color(255, 235, 246));
        playersInLobbyText.setText("PLAYERS IN LOBBY :");

        playerCountTextField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        playerCountTextField.setForeground(new java.awt.Color(255, 235, 246));
        playerCountTextField.setText("10");
        playerCountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerCountTextFieldActionPerformed(evt);
            }
        });

        instructionText.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        instructionText.setForeground(new java.awt.Color(255, 235, 246));
        instructionText.setText("HOW TO PLAY: JUST TYPE THE LONGEST WORD BASED ON THE GIVEN LETTERS TO WIN THE GAME");

        exitButton.setBackground(new java.awt.Color(255, 102, 102));
        exitButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        exitButton.setForeground(new java.awt.Color(255, 235, 246));
        exitButton.setText("EXIT LOBBY");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(logo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(waitingText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(countdownText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(countdownTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(playersInLobbyText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(playerCountTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(instructionText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(exitButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addGap(375, 375, 375)
                                                .addComponent(countdownText)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(countdownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                                .addGap(409, 409, 409)
                                                .addComponent(playersInLobbyText)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(playerCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addGap(0, 127, Short.MAX_VALUE)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(instructionText)
                                                .addGap(125, 125, 125))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(waitingText)
                                                .addGap(180, 180, 180))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(exitButton)
                                                .addGap(422, 422, 422))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(272, 272, 272))))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 911, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(waitingText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(countdownText)
                                        .addComponent(countdownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(playersInLobbyText)
                                        .addComponent(playerCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(instructionText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitButton)
                                .addContainerGap(11, Short.MAX_VALUE))
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>

    private void countdownTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void playerCountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LobbyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LobbyUI().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel countdownText;
    private javax.swing.JTextField countdownTextField;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel instructionText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField playerCountTextField;
    private javax.swing.JLabel playersInLobbyText;
    private javax.swing.JLabel waitingText;
    // End of variables declaration
}

