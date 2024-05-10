package dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class UsersPanel extends JPanel {
    private DefaultTableModel usersModel;

    public UsersPanel(DefaultTableModel model, ResourceBundle messages) {
        this.usersModel = model;
        JTable usersTable = new JTable(usersModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton(messages.getString("textForDeleteButton"));
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.NORTH);

        deleteButton.addActionListener(e -> {
            int selectedRow = usersTable.getSelectedRow(); // Get selected row index
            if (selectedRow != -1) {
                model.removeRow(selectedRow); // Remove selected row from personal database
                DeleteUsersData();
            } else {
                JOptionPane.showMessageDialog(this, messages.getString("textForUserDeleteError"), messages.getString("errorText"), JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    public void loadUsersData() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String username = data[0].trim();
                    String role = data[1].trim();
                    usersModel.addRow(new Object[]{username, role});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteUsersData() {
        try (FileWriter fw = new FileWriter("users.csv")) {
            for (int i = 0; i < usersModel.getRowCount(); i++) {
                String username = usersModel.getValueAt(i, 0).toString();
                String password = usersModel.getValueAt(i, 1).toString();
                fw.write(username + "," + password + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
