package Client_Java;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginUI extends JFrame {

    private JLabel jLabel1;
    private JPanel jPanel1;
    private java.awt.Label label10;
    private java.awt.Label signInLabel;
    private java.awt.Label passwordLabel;
    private java.awt.Label usernameLabel;
    private JButton loginButton;
    private JPasswordField password;
    private JTextField userName;
    public LoginUI() {
        label10 = new java.awt.Label();
        jPanel1 = new JPanel();
        signInLabel = new java.awt.Label();
        jLabel1 = new JLabel();
        usernameLabel = new java.awt.Label();
        userName = new JTextField();
        passwordLabel = new java.awt.Label();
        password = new JPasswordField();
        loginButton = new JButton();

        label10.setText("label2");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Window");
        setBackground(new java.awt.Color(255, 102, 255));
        setForeground(java.awt.Color.magenta);

        jPanel1.setBackground(new java.awt.Color(229, 209, 250));

        signInLabel.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        signInLabel.setForeground(new java.awt.Color(102, 0, 102));
        signInLabel.setText("Sign in");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 102));
        jLabel1.setText("Login with your registered account!");

        usernameLabel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(102, 0, 102));
        usernameLabel.setText("Username");

        userName.setBackground(new java.awt.Color(255, 244, 210));
        userName.setForeground(new java.awt.Color(102, 0, 102));
        userName.setToolTipText("Username");
        userName.setActionCommand("<Not Set>");
        userName.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        passwordLabel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(102, 0, 102));
        passwordLabel.setText("Password");

        password.setBackground(new java.awt.Color(255, 244, 210));
        password.setForeground(new java.awt.Color(102, 0, 102));
        password.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        loginButton.setBackground(new java.awt.Color(0, 204, 0));
        loginButton.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.setBorder(null);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });


        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(signInLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(userName)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(30, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
                                .addGap(1, 25, 100))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(signInLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)
                                .addComponent(usernameLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(userName, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(password, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addContainerGap(90, Short.MAX_VALUE))
        );

        signInLabel.getAccessibleContext().setAccessibleName("SigninText");
        usernameLabel.getAccessibleContext().setAccessibleName("UsernameText");
        usernameLabel.getAccessibleContext().setAccessibleDescription("");
        userName.getAccessibleContext().setAccessibleName("usernameField");
        passwordLabel.getAccessibleContext().setAccessibleName("PasswordText");
        password.getAccessibleContext().setAccessibleName("passwordField");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String user = userName.getText();
        String pass = String.valueOf(password.getPassword());
        if (user.isEmpty()){
            JOptionPane.showMessageDialog(null,"Username is empty");
        } else  if (pass.isEmpty()){
            JOptionPane.showMessageDialog(null,"Password is empty");
        }
        /*
        Testing of JDBC
        JDBC should be server-sided
         */
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = myConnection.getConnection().prepareStatement("SELECT * FROM `users` WHERE user_username =? AND user_password =?");
            preparedStatement.setString(1,user);
            preparedStatement.setString(2,pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("Successfully Logged-In!");
            } else {
                System.out.println("Fail");
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void startLogin() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginUI().setVisible(true);
            }
        });
    }
}
