package signuppage;

import javax.swing.*;

import loginpage.LoginPageGUI;

import java.awt.*;
import java.io.*;
import java.util.ResourceBundle;

public class SignUpPageGUI extends JFrame {
    private final JTextField nameField;
    private final JPasswordField passwordField;
    private final JPasswordField reEnterPasswordField;
    // @SuppressWarnings("deprecation")
    // private static ResourceBundle messages = ResourceBundle.getBundle("languages.messages", new Locale("az", "AZ"));

    public SignUpPageGUI(JFrame loginPageFrame, ResourceBundle messages) {
        setTitle("Sign Up");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(messages.getString("titleLabelTextForSignUp"));
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel(messages.getString("usernameLabelTextForSignUp"));
        nameField = new JTextField();
        JLabel passwordLabel = new JLabel(messages.getString("passwordLabelTextForSignUp"));
        passwordField = new JPasswordField();
        JLabel reEnterPasswordLabel = new JLabel(messages.getString("reEnterPasswordLabelForSignUp"));
        reEnterPasswordField = new JPasswordField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(reEnterPasswordLabel);
        inputPanel.add(reEnterPasswordField);

        mainPanel.add(inputPanel);

        JLabel haveAccountLabel = new JLabel(messages.getString("haveAccountLabel"));
        haveAccountLabel.setForeground(Color.BLUE);
        haveAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        haveAccountLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                dispose();
                loginPageFrame.setVisible(true);
            }
        });

        JButton signUpButton = new JButton(messages.getString("signUpButton"));
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(haveAccountLabel);
        mainPanel.add(signUpButton);

        signUpButton.addActionListener(e -> signUp(nameField.getText(), new String(passwordField.getPassword()), new String(reEnterPasswordField.getPassword()), messages));

        add(mainPanel);
        pack();
    }

    private void signUp(String username, String password, String reEnteredPassword, ResourceBundle messages) {
        if (username.isEmpty() || password.isEmpty() || reEnteredPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, messages.getString("validDataWarning"), messages.getString("warningText"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(reEnteredPassword)) {
            JOptionPane.showMessageDialog(this, messages.getString("passwordsNotMatchError"), messages.getString("errorText"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, messages.getString("passwordLengthWarning"), messages.getString("warningText"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.matches(".*[A-Z].*")) {
            JOptionPane.showMessageDialog(this, messages.getString("uppercaseLetterWarning"), messages.getString("warningText"), JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        if (!password.matches(".*[a-z].*")) {
            JOptionPane.showMessageDialog(this, messages.getString("lowercaseLetterWarning"), messages.getString("warningText"), JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        if (!password.matches(".*[!@#$%^&*()].*")) {
            JOptionPane.showMessageDialog(this, messages.getString("symbolWarning"), messages.getString("warningText"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (UserFileManagerForSignUp.checkUsername(username)) {
            JOptionPane.showMessageDialog(this, messages.getString("usernameExistsError"), messages.getString("errorText"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!UserFileManagerForSignUp.saveUser(username)) {
            JOptionPane.showMessageDialog(this, messages.getString("userCreationFailedError"), messages.getString("errorText"), JOptionPane.ERROR_MESSAGE);
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

    public static void setMessagesBundle(ResourceBundle bundle) {
        LoginPageGUI.messages = bundle;
    }
}