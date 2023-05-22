package Client_Java;

import javax.swing.*;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static Client_Java.Client.wordyCallback;
import static Client_Java.Client.wordyImpl;

public class LobbyUI extends javax.swing.JFrame{
    private javax.swing.JButton exitLobbyButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private static javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel matchTimerField;
    private javax.swing.JLabel playerCountField;
static String username;
    public LobbyUI(String username) {
        this.username = username;
        initComponents();
        addListeners();
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
        jLabel3.setText("WORDY LOBBY #2");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(520, 90, 215, 21);

        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(199, 283, 0, 0);

        jLabel5.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 236, 238));
        //jLabel5.setText("PLAYERS IN THE LOBBY :");
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

    private void addListeners()
    {
        // Add your listeners here as usual
        this.addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                inLobby();
                /* ... */
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }

            /* Other methods of WindowListener ... */
        });
    }

    /* ... */



    public void inLobby() {
        try {
            double lobbyCount = 0;
            double time = 0;
            if (wordyImpl != null) {
                boolean joined = wordyImpl.joinLobby(username);
                if (joined) {
                    int countdown = 5;
                    System.out.println(username + " Joined");
                    while (countdown > 0) {
                        countdown--;
                        System.out.println(countdown);
                        Thread.sleep(1000);
                        lobbyCount = wordyImpl.lobbyPlayerCount();
                        time = wordyImpl.gettimer() + 1;
                    }
                }
            }

            System.out.println("Players " + lobbyCount);
            while (time > 0) {
                lobbyCount = wordyImpl.lobbyPlayerCount();
                time--;
                if (lobbyCount >= 2) {
                    System.out.println("Count Down " + time);
                    jLabel3.setText("Starting Game: " + time);
                    Thread.sleep(1000);
                } else {
                    int result = JOptionPane.showConfirmDialog(null, "Not Enough Players!\nGo back to ClientUI?", "Error", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        dispose();
                        ClientUI.startClientUI(username);
                    }
                    break;
                }
            }

            if (time == 0) {
                gameStartNotification();
            }
        } catch (Exception e) {
            System.err.println("Error joining the lobby: " + e);
            e.printStackTrace(System.out);
        }
    }




    public void startGameUI(String username) {
        SwingUtilities.invokeLater(() -> {
            // Code to initialize and show the game UI
            GameUI.startGameUI(username);
            System.out.println("Game UI initialized for player: " + username);
        });
        dispose();
    }

    public void gameStartNotification() {
        SwingUtilities.invokeLater(() -> {
            // Update the UI to reflect the game start
            startGameUI(username);

        });
    }
    private void exitLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            wordyImpl.leaveGame(username);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

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


}
