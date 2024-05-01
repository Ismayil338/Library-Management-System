package dashboard;

import loginpage.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    public final String username;

    public Dashboard(String username, String role, boolean isAdmin) {
        this.username = username;
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents(username, role, isAdmin);
    }

    private void initComponents(String username, String role, boolean isAdmin) {
        JPanel topPanel = createTopPanel(username, role);
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Home Page", new HomePagePanel());

        if (role.equals("admin")) {
            DefaultTableModel usersModel = new DefaultTableModel();
            usersModel.addColumn("Username");
            usersModel.addColumn("Password");

            UsersPanel usersPanel = new UsersPanel(usersModel);
            usersPanel.loadUsersData();
            tabbedPane.addTab("Users", usersPanel);
        }
        
        if (!role.equals("admin")) {
            DefaultTableModel personalModel = new DefaultTableModel();
            personalModel.addColumn("Title");
            personalModel.addColumn("Author");
            personalModel.addColumn("Rating");
            personalModel.addColumn("Review");
            personalModel.addColumn("Status");
            personalModel.addColumn("Time spent");
            personalModel.addColumn("Start date");
            personalModel.addColumn("End Date");
            personalModel.addColumn("User rating");
            personalModel.addColumn("User review");

            PersonalDatabasePanel personalDatabasePanel = new PersonalDatabasePanel(personalModel);
            personalDatabasePanel.loadPersonalCsvData(username);

            tabbedPane.addTab("Personal Database", personalDatabasePanel);
        }

        DefaultTableModel generalModel = new DefaultTableModel();
        generalModel.addColumn("Title");
        generalModel.addColumn("Author");
        generalModel.addColumn("Review");
        generalModel.addColumn("Rating");

        GeneralDatabasePanel generalDatabasePanel = new GeneralDatabasePanel(generalModel, isAdmin);
        generalDatabasePanel.loadCSVData();

        tabbedPane.addTab("General Database", generalDatabasePanel);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createTopPanel(String username, String role) {
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome to Library", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 21));
        topPanel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel userDetailsLabel = new JLabel("Welcome, " + username + " | Role: " + role, SwingConstants.RIGHT);
        topPanel.add(userDetailsLabel, BorderLayout.SOUTH);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            LoginPageGUI loginPage = new LoginPageGUI();
            loginPage.setVisible(true);
        });
        topPanel.add(logoutButton, BorderLayout.WEST);

        return topPanel;
    }
}
