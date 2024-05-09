package signuppage;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SignUpPageGUI extends JFrame {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JPasswordField reEnterPasswordField;

    public SignUpPageGUI(JFrame loginPageFrame) {
        setTitle("Sign Up");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Create an Account");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Username:");
        nameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel reEnterPasswordLabel = new JLabel("Re-enter Password:");
        reEnterPasswordField = new JPasswordField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(reEnterPasswordLabel);
        inputPanel.add(reEnterPasswordField);

        mainPanel.add(inputPanel);

        JLabel haveAccountLabel = new JLabel("Do you have account?");
        haveAccountLabel.setForeground(Color.BLUE);
        haveAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        haveAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                dispose();
                loginPageFrame.setVisible(true);
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(haveAccountLabel);
        mainPanel.add(signUpButton);

        signUpButton.addActionListener(e -> signUp(nameField.getText(), new String(passwordField.getPassword()), new String(reEnterPasswordField.getPassword())));

        add(mainPanel);
        pack();
    }

    private void signUp(String username, String password, String reEnteredPassword) {
        if (username.isEmpty() || password.isEmpty() || reEnteredPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter valid data for creating account", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(reEnteredPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.matches(".*[A-Z].*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one uppercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!password.matches(".*[a-z].*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one lowercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!password.matches(".*[!@#$%^&*()].*")) {
            JOptionPane.showMessageDialog(this, "Password must contain at least one symbol", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UserFileManager.checkUsername(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!UserFileManager.saveUser(username)) {
            JOptionPane.showMessageDialog(this, "Failed to create user. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String userData = username + "," + password;

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("users.csv", true))) {
            printWriter.println(userData);
            JOptionPane.showMessageDialog(this, "User created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}