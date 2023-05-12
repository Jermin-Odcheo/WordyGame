package Client_Java;

import javax.swing.*;

import static Client_Java.Client.wordyImpl;

public class GameUI extends javax.swing.JFrame {

    static String username;
    public GameUI(String username) {
        this.username = username;
        initComponents();
        String letters = wordyImpl.generateLetters();
        jTextArea1.append(letters);
    }


    private void initComponents() {


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

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

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
        getContentPane().add(timerField);
        timerField.setBounds(1000, 30, 70, 50);

        jLabel3.setIcon(new javax.swing.ImageIcon("src/Client_Java/WORD (5).png")); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(710, -20, 380, 150);

        jLabel7.setIcon(new javax.swing.ImageIcon("src/Client_Java/17250835.png")); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(0, 0, 1110, 770);

        setBounds(0, 0, 1125, 776);
    }

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
        wordyImpl.playWord(username,inputField.getText());}
        catch (Exception e){
            System.out.println(e.getMessage());
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea playerListField;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel timerField;
    private javax.swing.JTextArea wordBoxField;
}

