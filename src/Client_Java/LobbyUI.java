package Client_Java;

import java.awt.*;

public class LobbyUI extends javax.swing.JFrame {

    public LobbyUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel3 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        logo = new javax.swing.JLabel();
        waitingTextField = new javax.swing.JLabel();
        matchCountdownText = new javax.swing.JLabel();
        countdownTextField = new javax.swing.JTextField();
        exitLobbyButton = new javax.swing.JButton();
        playerInLobbyText = new javax.swing.JLabel();
        playerCountTextField = new javax.swing.JTextField();
        instructionTextField = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setIcon(new javax.swing.ImageIcon("src/Client_Java/17250835.png")); // NOI18N

        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 966, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 966, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 564, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
        );

        logo.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png")); // NOI18N
        logo.setToolTipText("");

        waitingTextField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        waitingTextField.setForeground(new java.awt.Color(253, 244, 245));
        waitingTextField.setText("WAITING FOR PLAYERS TO JOIN");

        matchCountdownText.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        matchCountdownText.setForeground(new java.awt.Color(253, 244, 245));
        matchCountdownText.setText("MATCH STARTS IN");

        countdownTextField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        countdownTextField.setForeground(new java.awt.Color(255, 255, 255));
        countdownTextField.setText("10");
        countdownTextField.setBackground(new Color(0,0,0,1));
        countdownTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countdownTextFieldActionPerformed(evt);
            }
        });


        exitLobbyButton.setBackground(new java.awt.Color(255, 102, 153));
        exitLobbyButton.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 12)); // NOI18N
        exitLobbyButton.setForeground(new java.awt.Color(255, 255, 255));
        exitLobbyButton.setText("EXIT LOBBY");
        exitLobbyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitLobbyButtonActionPerformed(evt);
            }
        });

        playerInLobbyText.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        playerInLobbyText.setForeground(new java.awt.Color(253, 244, 245));
        playerInLobbyText.setText("PLAYERS IN LOBBY :");
        playerInLobbyText.setToolTipText("");

        playerCountTextField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        playerCountTextField.setForeground(new java.awt.Color(255, 255, 255));
        playerCountTextField.setText("10");
        playerCountTextField.setBackground(new Color(0,0,0,1));
        playerCountTextField.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                playerCountTextFieldActionPerformed(evt);
            }
        });

        instructionTextField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        instructionTextField.setForeground(new java.awt.Color(253, 244, 245));
        instructionTextField.setText("HOW TO PLAY: FORM THE LONGEST WORD BASED ON THE GIVEN LETTERS TO WIN");

        jLayeredPane2.setLayer(logo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(waitingTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(matchCountdownText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(countdownTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(exitLobbyButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(playerInLobbyText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(playerCountTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(instructionTextField, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
                jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                                .addGap(380, 380, 380)
                                                .addComponent(matchCountdownText, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                                .addGap(404, 404, 404)
                                                .addComponent(playerInLobbyText, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(countdownTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(playerCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                .addGap(66, 175, Short.MAX_VALUE)
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                                .addComponent(waitingTextField)
                                                .addGap(184, 184, 184))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                                .addComponent(exitLobbyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(388, 388, 388))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                                .addComponent(logo)
                                                .addGap(246, 246, 246))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                                .addComponent(instructionTextField)
                                                .addGap(143, 143, 143))))
        );
        jLayeredPane2Layout.setVerticalGroup(
                jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(waitingTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(matchCountdownText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(countdownTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(playerInLobbyText)
                                        .addComponent(playerCountTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(instructionTextField)
                                .addGap(18, 18, 18)
                                .addComponent(exitLobbyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 4, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane2))
        );

        pack();
    }// </editor-fold>

    private void exitLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void countdownTextFieldActionPerformed(java.awt.event.ActionEvent evt){

    }

    private void playerCountTextFieldActionPerformed(java.awt.event.ActionEvent evt){

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

    private javax.swing.JTextField countdownTextField;
    private javax.swing.JButton exitLobbyButton;
    private javax.swing.JLabel instructionTextField;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel matchCountdownText;
    private javax.swing.JTextField playerCountTextField;
    private javax.swing.JLabel playerInLobbyText;
    private javax.swing.JLabel waitingTextField;
    // End of variables declaration
}

