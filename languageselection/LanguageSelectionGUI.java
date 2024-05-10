package languageselection;

import javax.swing.*;

import loginpage.LoginPageGUI;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageSelectionGUI extends JFrame {
    private final JComboBox<String> languageComboBox;

    @SuppressWarnings("deprecation")
    public LanguageSelectionGUI() {
        setTitle("Language Selection");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel languageLabel = new JLabel("Select Language:");
        languageComboBox = new JComboBox<>(new String[] { "English", "Azerbaijani" });
        languagePanel.add(languageLabel);
        languagePanel.add(languageComboBox);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            String selectedLanguage = (String) languageComboBox.getSelectedItem();
            Locale newLocale = selectedLanguage.equals("English") ? new Locale("en", "US") : new Locale("az", "AZ");
            LoginPageGUI.setMessagesBundle(ResourceBundle.getBundle("languages.messages", newLocale));
            dispose();
            EventQueue.invokeLater(LoginPageGUI::new);                                                                        
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(confirmButton);

        add(languagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
