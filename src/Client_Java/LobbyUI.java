package Client_Java;

import javax.swing.*;
import java.awt.*;

import static Client_Java.Client.wordyImpl;

public class LobbyUI extends javax.swing.JFrame{
static String username;
    public LobbyUI(String username) {
        this.username = username;
        initComponents();
        inLobby();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        matchTimerField = new javax.swing.JLabel();
        playerCountField = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        exitLobbyButton = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1270, 750));
        getContentPane().setLayout(null);

        jLabel1.setForeground(new java.awt.Color(255, 236, 238));
        jLabel1.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png")); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(430, 130, 438, 438);

        jLabel2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 236, 238));
        jLabel2.setText("WAITING FOR PLAYERS TO JOIN");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(342, 30, 609, 43);

        jLabel3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 236, 238));
        jLabel3.setText("MATCH WILL START IN");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(520, 90, 215, 21);

        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(199, 283, 0, 0);

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 236, 238));
        jLabel5.setText("PLAYERS IN THE LOBBY :");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(530, 120, 184, 17);

        matchTimerField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        matchTimerField.setForeground(new java.awt.Color(255, 236, 238));
        getContentPane().add(matchTimerField);
        matchTimerField.setBounds(740, 90, 30, 20);

        playerCountField.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        playerCountField.setForeground(new java.awt.Color(255, 236, 238));
        getContentPane().add(playerCountField);
        playerCountField.setBounds(720, 120, 20, 17);

        jLabel8.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 236, 238));
        jLabel8.setText("HOW TO PLAY:");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(590, 570, 107, 17);

        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 236, 238));
        jLabel9.setText("Type the Longest word or Type the most words within the given time for you to win the game.");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(310, 600, 659, 17);

        exitLobbyButton.setBackground(new java.awt.Color(255, 102, 102));
        exitLobbyButton.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        exitLobbyButton.setForeground(new java.awt.Color(255, 255, 255));
        exitLobbyButton.setText("EXIT LOBBY");
        exitLobbyButton.setBorderPainted(false);
        exitLobbyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitLobbyButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exitLobbyButton);
        exitLobbyButton.setBounds(560, 630, 160, 50);

        jLabel7.setIcon(new javax.swing.ImageIcon("src/Client_Java/17250835.png")); // NOI18N

        jLayeredPane1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1263, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, Short.MAX_VALUE)
                                        .addContainerGap()))
        );
        jLayeredPane1Layout.setVerticalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 711, Short.MAX_VALUE)
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 711, Short.MAX_VALUE))
        );

        getContentPane().add(jLayeredPane1);
        jLayeredPane1.setBounds(0, 0, 1260, 710);

        pack();

    }






    public static void inLobby(){
        try {
            boolean joined = wordyImpl.joinGame(username);
            if (joined) {
                System.out.println("Successfully joined the lobby!");
                wordyImpl.timer();
            } else {
                JOptionPane.showMessageDialog(null,"Failed to join the lobby. Please try again later.");
                System.out.println("No other players joined. Exiting lobby.");
                wordyImpl.leaveGame(username);
                ClientUI.startClientUI(username);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    private void exitLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {
       dispose();
    }

    public static void startLobby(String username) {

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
                new LobbyUI(username).setVisible(true);

            }
        });
    }

    private javax.swing.JButton exitLobbyButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel matchTimerField;
    private javax.swing.JLabel playerCountField;

}
