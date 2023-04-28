package Client_Java;

public class GameUI extends javax.swing.JFrame {

    public GameUI() {
        initComponents();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        playerListField = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        wordBoxField = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        yourLettersField = new javax.swing.JTextField();
        inputField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        playerListField.setColumns(20);
        playerListField.setRows(5);
        playerListField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 153, 255)));
        jScrollPane1.setViewportView(playerListField);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(790, 130, 226, 300);

        wordBoxField.setColumns(20);
        wordBoxField.setRows(5);
        wordBoxField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 102, 255)));
        jScrollPane2.setViewportView(wordBoxField);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(22, 130, 716, 300);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WordyGame Beta v3.32.12");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(22, 15, 490, 48);

        yourLettersField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        yourLettersField.setForeground(new java.awt.Color(255, 153, 0));
        yourLettersField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 51, 255)));
        yourLettersField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yourLettersFieldActionPerformed(evt);
            }
        });
        getContentPane().add(yourLettersField);
        yourLettersField.setBounds(30, 500, 410, 47);

        inputField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        inputField.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(51, 153, 255)));
        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });
        getContentPane().add(inputField);
        inputField.setBounds(30, 560, 716, 50);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Word Box");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 100, 117, 25);

        sendButton.setBackground(new java.awt.Color(0, 153, 204));
        sendButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        sendButton.setForeground(new java.awt.Color(255, 255, 255));
        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        getContentPane().add(sendButton);
        sendButton.setBounds(800, 500, 180, 70);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Your Letters:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 460, 117, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Player List");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(790, 100, 117, 25);

        jLabel6.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png")); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(540, 330, 410, 310);

        jLabel7.setIcon(new javax.swing.ImageIcon("src/Client_Java/17250835.png")); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(0, 0, 1070, 670);

        setBounds(0, 0, 1068, 680);
    }

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void yourLettersFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameUI().setVisible(true);
            }
        });
    }

    private javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea playerListField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea wordBoxField;
    private javax.swing.JTextField yourLettersField;

}
