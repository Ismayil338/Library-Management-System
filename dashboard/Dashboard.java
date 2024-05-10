package dashboard;

import loginpage.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class Dashboard extends JFrame {
    public final String username;

    public Dashboard(String username, String role, boolean isAdmin, ResourceBundle messages) {
        this.username = username;
        setTitle(messages.getString("titleLabelTextForDashboard"));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents(username, role, isAdmin, messages);
    }

    private void initComponents(String username, String role, boolean isAdmin, ResourceBundle messages) {
        JPanel topPanel = createTopPanel(username, role, messages);
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(messages.getString("textForHomePage"), new HomePagePanel());

        if (role.equals("admin")) {
            DefaultTableModel usersModel = new DefaultTableModel();
            usersModel.addColumn(messages.getString("usersPanelUsernameColumn"));
            usersModel.addColumn(messages.getString("usersPanelPasswordColumn"));

            UsersPanel usersPanel = new UsersPanel(usersModel, messages);
            usersPanel.loadUsersData();
            tabbedPane.addTab(messages.getString("textForUsersPanel"), usersPanel);
        }
        
        if (!role.equals("admin")) {
            DefaultTableModel personalModel = new DefaultTableModel();
            personalModel.addColumn(messages.getString("textForPersonalDatabaseTitleColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseAuthorColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseReviewColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseRatingColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseStatusColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseTimeSpentColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseStartDateColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseEndDateColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseUserReviewColumn"));
            personalModel.addColumn(messages.getString("textForPersonalDatabaseUserRatingColumn"));

            PersonalDatabasePanel personalDatabasePanel = new PersonalDatabasePanel(personalModel, username, messages);
            personalDatabasePanel.loadPersonalCsvData(username);

            tabbedPane.addTab(messages.getString("textForPersonalDatabasePanel"), personalDatabasePanel);
        }

        DefaultTableModel generalModel = new DefaultTableModel();
        generalModel.addColumn(messages.getString("textForPersonalDatabaseTitleColumn"));
        generalModel.addColumn(messages.getString("textForPersonalDatabaseAuthorColumn"));
        generalModel.addColumn(messages.getString("textForPersonalDatabaseReviewColumn"));
        generalModel.addColumn(messages.getString("textForPersonalDatabaseRatingColumn"));

        GeneralDatabasePanel generalDatabasePanel = new GeneralDatabasePanel(username, generalModel, isAdmin, messages);
        String sourceFilePath = "generaldatabase/brodsky.csv";
        String destinationFilePath = "generaldatabase/generaldatabase.csv";
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        File csvFile = new File("generaldatabase/generaldatabase.csv");
        if (!csvFile.exists()) {
            try {
                csvFile.createNewFile();
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                generalDatabasePanel.loadCSVData(messages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        generalDatabasePanel.loadCSVDataAfterDatabaseCreated();

        tabbedPane.addTab(messages.getString("textForGeneralDatabasePanel"), generalDatabasePanel);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTopPanel(String username, String role, ResourceBundle messages) {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel(messages.getString("textForDashboardWelcome"), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 21));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel userDetailsLabel = new JLabel(messages.getString("textForUserWelcome") + " " + username + " | Role: " + role, SwingConstants.RIGHT);
        topPanel.add(userDetailsLabel, BorderLayout.SOUTH);

        JButton logoutButton = new JButton(messages.getString("textForLogOutButton"));
        logoutButton.addActionListener(e -> {
            dispose();
            LoginPageGUI loginPage = new LoginPageGUI();
            loginPage.setVisible(true);
        });
        topPanel.add(logoutButton, BorderLayout.WEST);

        return topPanel;
    }
}