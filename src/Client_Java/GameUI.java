package Client_Java;

import Server_Java.corba.GameException;
import Server_Java.corba.InvalidWord;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import static Client_Java.Client.wordyImpl;

public class GameUI extends javax.swing.JFrame{
    static String username;
    static JLabel timerField;
    static int countdownSeconds = 30;
    public GameUI(String username) {
        this.username = username;
        initComponents();
        addListeners();
        startCountdownTimer();
    }

    public void addListeners()
    {
        // Add your listeners here as usual
        this.addWindowListener(new WindowListener()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                System.out.println(wordyImpl.playerInGameList());
                playerListField.append(wordyImpl.playerInGameList().replace(",", "\n").substring(1, wordyImpl.playerInGameList().length() - 1));
                jTextArea1.append(wordyImpl.getGeneratedLetter());
                System.out.println(wordyImpl.getGeneratedLetter());


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

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerListField = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        wordBoxField = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        inputField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        timerField = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelcheck = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1125, 800));
        setPreferredSize(new java.awt.Dimension(1125, 800));
        getContentPane().setLayout(null);
        jPanel1.setBackground(new java.awt.Color(204, 153, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1125, 800));
        jPanel1.setPreferredSize(new java.awt.Dimension(1125, 800));
        playerListField.setBackground(new java.awt.Color(212, 250, 252));
        playerListField.setColumns(20);
        playerListField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        playerListField.setForeground(new java.awt.Color(0, 102, 204));
        playerListField.setRows(5);
        playerListField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 153, 255)));
        jScrollPane1.setViewportView(playerListField);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(790, 130, 266, 300);

        wordBoxField.setBackground(new java.awt.Color(227, 223, 253));
        wordBoxField.setColumns(20);
        wordBoxField.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        wordBoxField.setForeground(new java.awt.Color(204, 0, 204));
        wordBoxField.setRows(5);
        wordBoxField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 102, 255)));
        jScrollPane2.setViewportView(wordBoxField);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(22, 130, 716, 310);

        jTextArea1.setBackground(new java.awt.Color(255, 204, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 51, 204));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 51, 255)));
        jScrollPane3.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(790, 480, 270, 240);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WordyGame Beta v3.32.12");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(22, 15, 490, 48);

        inputField.setBackground(new java.awt.Color(204, 204, 255));
        inputField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        inputField.setForeground(new java.awt.Color(204, 51, 255));
        inputField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 204, 153)));
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });
        getContentPane().add(inputField);
        inputField.setBounds(30, 510, 530, 50);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Word Box");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 100, 117, 25);

        sendButton.setBackground(new java.awt.Color(255, 51, 255));
        sendButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        sendButton.setForeground(new java.awt.Color(255, 255, 255));
        sendButton.setText("Enter");
        sendButton.setBorderPainted(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(sendButton);
        sendButton.setBounds(220, 590, 160, 50);

        jLabelcheck.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18)); // NOI18N
        jLabelcheck.setForeground(new java.awt.Color(255, 255, 255));
        jLabelcheck.setText(" ");
        getContentPane().add(jLabelcheck);
        jLabelcheck.setBounds(190, 630, 250, 100);


        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Your Letters");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(790, 450, 117, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Player List");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(790, 100, 117, 25);

        jLabel6.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png")); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(400, 440, 410, 310);

        timerField.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        timerField.setForeground(new java.awt.Color(255, 255, 255));
        timerField.setText("");
        getContentPane().add(timerField);
        timerField.setBounds(900, 30, 70, 50);

        jLabel3.setIcon(new javax.swing.ImageIcon("src/Client_Java/WORD (5).png")); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(710, -20, 380, 150);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1125, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(2, 2, 2)
                                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(198, 198, 198)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(290, 290, 290)
                                                                        .addComponent(timerField, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(80, 80, 80)
                                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(2, 2, 2)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(52, 52, 52)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(190, 190, 190)
                                                                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(20, 20, 20)
                                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(760, 760, 760)
                                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                        .addGap(760, 760, 760)
                                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 800, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(35, 35, 35)
                                                        .addComponent(jLabel1)
                                                        .addGap(37, 37, 37)
                                                        .addComponent(jLabel2))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(50, 50, 50)
                                                        .addComponent(timerField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(20, 20, 20)
                                                        .addComponent(jLabel5))
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(150, 150, 150)
                                                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addComponent(jLabel4))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(70, 70, 70)
                                                        .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1110, 770);

        setBounds(0, 0, 1125, 776);
    }


    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String word = inputField.getText();
            if (word.length() < 5) {
                jLabelcheck.setText("WORD IS TOO SHORT!");
                Timer timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jLabelcheck.setText(" ");
                    }
                });
                timer.start();
            } else {
                wordBoxField.append("You Played The Word: " + word + "\n");
                wordyImpl.playWord(username, word);
            }
        } catch (InvalidWord e) {
            try {
            Document document = wordBoxField.getDocument();
            int length = document.getLength();
            int lastNewlineIndex = 0;

                lastNewlineIndex = wordBoxField.getLineOfOffset(length - 1);

            int startOffset = wordBoxField.getLineStartOffset(lastNewlineIndex);
            int endOffset = wordBoxField.getLineEndOffset(lastNewlineIndex);

            document.remove(startOffset, endOffset - startOffset);
            jLabelcheck.setText("Invalid Word!");
            System.out.println("Exception occurred on the server: " + e.getMessage());
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void startGameUI(String username) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new GameUI(username).setVisible(true);
            }
        });
    }

    private static javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelcheck;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea playerListField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea wordBoxField;
    private javax.swing.JPanel jPanel1;
    private void startCountdownTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            int remainingSeconds = countdownSeconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                timerField.setText(String.valueOf(remainingSeconds));
                System.out.println(remainingSeconds);
                if (remainingSeconds == 0) {
                    // Timer has reached 0, perform any necessary actions here

                    JOptionPane.showMessageDialog(null,wordyImpl.getWinner());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    // Stop the timer
                    ((Timer) e.getSource()).stop();

                    try {
                        wordyImpl.joinLobby(username);
                    } catch (GameException ex) {
                        throw new RuntimeException(ex);
                    }
                    jTextArea1.setText(wordyImpl.getGeneratedLetter());
                    System.out.println(wordyImpl.getGeneratedLetter());
                    wordBoxField.setText(" ");
                    startCountdownTimer();
                }

                remainingSeconds--;
            }
        });

        timer.start();
    }
}

