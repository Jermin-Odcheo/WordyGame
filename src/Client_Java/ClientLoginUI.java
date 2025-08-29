package Client_Java;


import corbaGame.checkLogin;
import corbaGame.invalid;
import corbaGame.notFound;
import corbaGame.validatedLogin;

import javax.swing.*;

import static Client_Java.Client.wordyImpl;

public class ClientLoginUI extends javax.swing.JFrame {

    static { Client_Java.util.UIUtils.applyModernNimbusTweaks(); }

    public ClientLoginUI() {
        initComponents();
    }

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        userName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        password = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("WordyGame - Login");
        setResizable(true);
        setSize(900, 560);
        setLocationRelativeTo(null);

        // Main container
        jPanel1 = new JPanel(new java.awt.BorderLayout(20, 20));
        jPanel1.setBackground(new java.awt.Color(45, 52, 70));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Left branding panel
        JPanel leftBrand = new JPanel();
        leftBrand.setBackground(new java.awt.Color(31, 41, 55));
        leftBrand.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(75, 85, 99), 2, true),
            javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        leftBrand.setLayout(new BoxLayout(leftBrand, BoxLayout.Y_AXIS));

        jLabel2 = new JLabel("WordyGame", SwingConstants.CENTER);
        jLabel2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        jLabel2.setForeground(java.awt.Color.WHITE);
        jLabel2.setAlignmentX(CENTER_ALIGNMENT);

        jLabel1 = new JLabel("Exercise your mind while having fun!", SwingConstants.CENTER);
        jLabel1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        jLabel1.setForeground(new java.awt.Color(209, 213, 219));
        jLabel1.setAlignmentX(CENTER_ALIGNMENT);

        jLabel7 = new JLabel();
        jLabel7.setAlignmentX(CENTER_ALIGNMENT);
        jLabel7.setIcon(new javax.swing.ImageIcon("src/Server_Java/WORD.png"));

        jLabel8 = new JLabel("Brought to you by Odcheo LLC.", SwingConstants.CENTER);
        jLabel8.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jLabel8.setForeground(new java.awt.Color(168, 162, 158));
        jLabel8.setAlignmentX(CENTER_ALIGNMENT);

        jLabel9 = new JLabel("Client.10.17.22", SwingConstants.CENTER);
        jLabel9.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        jLabel9.setForeground(new java.awt.Color(168, 162, 158));
        jLabel9.setAlignmentX(CENTER_ALIGNMENT);

        leftBrand.add(Box.createVerticalStrut(10));
        leftBrand.add(jLabel2);
        leftBrand.add(Box.createVerticalStrut(10));
        leftBrand.add(jLabel1);
        leftBrand.add(Box.createVerticalStrut(20));
        leftBrand.add(jLabel7);
        leftBrand.add(Box.createVerticalStrut(20));
        leftBrand.add(jLabel8);
        leftBrand.add(Box.createVerticalStrut(10));
        leftBrand.add(jLabel9);

        // Right login panel
        jPanel2 = new JPanel();
        jPanel2.setBackground(new java.awt.Color(55, 65, 81));
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(107, 114, 128), 2, true),
            javax.swing.BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.Y_AXIS));

        jLabel5 = new JLabel("Login", SwingConstants.CENTER);
        jLabel5.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        jLabel5.setForeground(java.awt.Color.WHITE);
        jLabel5.setAlignmentX(CENTER_ALIGNMENT);

        jLabel3.setForeground(new java.awt.Color(209, 213, 219));
        jLabel3.setText("Username");
        jLabel3.setAlignmentX(CENTER_ALIGNMENT);

        userName.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        userName.setBackground(new java.awt.Color(17, 24, 39));
        userName.setForeground(java.awt.Color.WHITE);
        userName.setCaretColor(java.awt.Color.WHITE);
        userName.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(59, 130, 246), 2, true),
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        userName.addActionListener(this::jTextField2ActionPerformed);
        userName.setAlignmentX(CENTER_ALIGNMENT);
        userName.setColumns(18);
        userName.setMaximumSize(new java.awt.Dimension(280, 36));
        userName.setPreferredSize(new java.awt.Dimension(280, 36));

        jLabel4.setForeground(new java.awt.Color(209, 213, 219));
        jLabel4.setText("Password");
        jLabel4.setAlignmentX(CENTER_ALIGNMENT);

        password.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        password.setBackground(new java.awt.Color(17, 24, 39));
        password.setForeground(java.awt.Color.WHITE);
        password.setCaretColor(java.awt.Color.WHITE);
        password.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            new javax.swing.border.LineBorder(new java.awt.Color(59, 130, 246), 2, true),
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        password.setAlignmentX(CENTER_ALIGNMENT);
        password.setColumns(18);
        password.setMaximumSize(new java.awt.Dimension(280, 36));
        password.setPreferredSize(new java.awt.Dimension(280, 36));

        loginButton = new JButton("Login");
        loginButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        loginButton.setForeground(java.awt.Color.WHITE);
        loginButton.setBackground(new java.awt.Color(34, 197, 94));
        loginButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(CENTER_ALIGNMENT);
        loginButton.setMaximumSize(new java.awt.Dimension(280, 40));
        loginButton.setPreferredSize(new java.awt.Dimension(280, 40));
        loginButton.addActionListener(this::loginButtonActionPerformed);

        // assemble right panel with vertical centering
        jPanel2.add(Box.createVerticalGlue());
        jPanel2.add(jLabel5);
        jPanel2.add(Box.createVerticalStrut(18));
        jPanel2.add(jLabel3);
        jPanel2.add(Box.createVerticalStrut(6));
        jPanel2.add(userName);
        jPanel2.add(Box.createVerticalStrut(14));
        jPanel2.add(jLabel4);
        jPanel2.add(Box.createVerticalStrut(6));
        jPanel2.add(password);
        jPanel2.add(Box.createVerticalStrut(20));
        jPanel2.add(loginButton);
        jPanel2.add(Box.createVerticalGlue());

        // Put into main panel
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftBrand, jPanel2);
        split.setResizeWeight(0.5);
        split.setBorder(null);
        split.setOpaque(false);
        jPanel1.add(split, java.awt.BorderLayout.CENTER);

        setContentPane(jPanel1);
        validate();
        repaint();
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
        // no-op
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Disable login button to prevent multiple clicks
        loginButton.setEnabled(false);
        loginButton.setText("Connecting...");

        String usernameText = userName.getText().trim();
        String passwordText = new String(password.getPassword()).trim();

        // Validate input
        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Input Error", JOptionPane.WARNING_MESSAGE);
            resetLoginButton();
            return;
        }

        // Run connection and login in background thread to avoid UI freezing
        SwingWorker<Boolean, String> loginWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // First, try to establish connection to server
                publish("Connecting to server...");

                if (!Client.connectToServer(5)) { // Try 5 times to connect
                    return false;
                }

                publish("Authenticating...");

                // Try to login
                try {
                    wordyImpl.login(usernameText, passwordText);
                    return false; // This shouldn't happen if login is successful
                } catch (validatedLogin vl) {
                    // Success - this is actually the successful case
                    publish("Login successful!");
                    return true;
                } catch (checkLogin cl) {
                    publish("ERROR: User already logged in!");
                    return false;
                } catch (notFound nf) {
                    publish("ERROR: Account not found!");
                    return false;
                } catch (invalid inv) {
                    publish("ERROR: Invalid credentials!");
                    return false;
                } catch (Exception e) {
                    publish("ERROR: Connection failed - " + e.getMessage());
                    return false;
                }
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                // Update UI with progress messages
                for (String message : chunks) {
                    if (message.startsWith("ERROR:")) {
                        // Show error in dialog and reset button
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(ClientLoginUI.this,
                                    message.substring(6), // Remove "ERROR:" prefix
                                    "Login Error",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                        loginButton.setText("Login Failed");
                    } else {
                        loginButton.setText(message);
                    }
                }
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    if (success) {
                        // Login successful - redirect to ClientUI first
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(ClientLoginUI.this,
                                    "Welcome " + usernameText + "! Redirecting to main menu...",
                                    "Login Successful",
                                    JOptionPane.INFORMATION_MESSAGE);

                            // Close login window and go to ClientUI
                            dispose();
                            ClientUI.startClientUI(usernameText);
                        });

                    } else {
                        // Login failed - reset button after short delay
                        Timer resetTimer = new Timer(2000, e -> resetLoginButton());
                        resetTimer.setRepeats(false);
                        resetTimer.start();
                    }
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(ClientLoginUI.this,
                                "Unexpected error during login: " + e.getMessage(),
                                "System Error",
                                JOptionPane.ERROR_MESSAGE);
                        resetLoginButton();
                    });
                }
            }
        };

        loginWorker.execute();
    }

    private void resetLoginButton() {
        loginButton.setEnabled(true);
        loginButton.setText("Login");
    }

    public static void startLogin() {
        Client_Java.util.UIUtils.applyModernNimbusTweaks();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientLoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientLoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientLoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientLoginUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientLoginUI().setVisible(true);
            }
        });
    }


    private javax.swing.JButton loginButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField userName;

}
