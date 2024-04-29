package loginpage;

import signuppage.*;
import dashboard.*;
import javax.swing.*;
import java.awt.*;

public class LoginPageGUI extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginPageGUI() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Welcome to Library");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        JPanel formPanel = new JPanel(new GridLayout(2, 1));
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        formPanel.add(usernamePanel);
        formPanel.add(passwordPanel);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JLabel signUpLabel = new JLabel("Don't have an account?", SwingConstants.CENTER);
        signUpLabel.setForeground(Color.BLUE);
        JButton loginButton = new JButton("Login");

        signUpLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                EventQueue.invokeLater(() -> {
                    SwingUtilities.invokeLater(() -> {
                        JFrame signUpFrame = new SignUpPageGUI(LoginPageGUI.this);
                        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        signUpFrame.setVisible(true);
                    });

                });
            }
        });

        loginButton.addActionListener(e -> login(usernameField.getText(), new String(passwordField.getPassword())));

        JPanel buttonCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonCenterPanel.add(loginButton);
        buttonsPanel.add(signUpLabel, BorderLayout.NORTH);
        buttonsPanel.add(buttonCenterPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter valid username and password", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!UserFileManager.checkCredentials(username, password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String role = "user";
        if (username.equals("admin") && password.equals("admin")) {
            role = "admin"; 
        }

        openDashboard(username, role);
    }

    private void openDashboard(String username, String role) {
        dispose();
        EventQueue.invokeLater(() -> SwingUtilities.invokeLater(() -> {
            Dashboard dashboardGUI = new Dashboard(username, role);
            dashboardGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboardGUI.setVisible(true);
        }));
    }
}

