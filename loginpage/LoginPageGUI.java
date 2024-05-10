package loginpage;

import signuppage.*;
import dashboard.*;
import languageselection.LanguageSelectionGUI;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginPageGUI extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    @SuppressWarnings("deprecation")
    public static ResourceBundle messages = ResourceBundle.getBundle("languages.messages", new Locale("az", "AZ"));

    public LoginPageGUI() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(messages.getString("titleLabelTextForLogin"));
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        JPanel formPanel = new JPanel(new GridLayout(2, 1));
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel(messages.getString("usernameLabelTextForLogin"));
        usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel(messages.getString("passwordLabelTextForLogin"));
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        formPanel.add(usernamePanel);
        formPanel.add(passwordPanel);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JLabel signUpLabel = new JLabel(messages.getString("signUpLabelText"), SwingConstants.CENTER);
        signUpLabel.setForeground(Color.BLUE);
        JButton loginButton = new JButton(messages.getString("loginButtonText"));
        JButton returnToLanguageChangeWindow = new JButton(messages.getString("returnToLanguageChangeWindow"));

        signUpLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        signUpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                EventQueue.invokeLater(() -> SwingUtilities.invokeLater(() -> {
                    JFrame signUpFrame = new SignUpPageGUI(LoginPageGUI.this, messages);
                    signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    signUpFrame.setVisible(true);
                }));
            }
        });

        loginButton.addActionListener(e -> login(usernameField.getText(), new String(passwordField.getPassword())));

        returnToLanguageChangeWindow.addActionListener(e -> openLanguageSelection());

        JPanel buttonCenterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonCenterPanel.add(loginButton);
        buttonCenterPanel.add(returnToLanguageChangeWindow);
        buttonsPanel.add(signUpLabel, BorderLayout.NORTH);
        buttonsPanel.add(buttonCenterPanel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, messages.getString("validCredentialsWarning"), messages.getString("warningText"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!UserFileManagerForLogin.checkCredentials(username, password)) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", messages.getString("errorText"), JOptionPane.ERROR_MESSAGE);
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
        boolean isAdmin = role.equals("admin");
        EventQueue.invokeLater(() -> SwingUtilities.invokeLater(() -> {
            Dashboard dashboardGUI = new Dashboard(username, role, isAdmin, messages);
            dashboardGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboardGUI.setVisible(true);
        }));
    }

    public static void setMessagesBundle(ResourceBundle bundle) {
        messages = bundle;
    }

    public void openLanguageSelection() {
        dispose(); // Close the login page
        EventQueue.invokeLater(LanguageSelectionGUI::new); // Open the language selection GUI
    }
}
