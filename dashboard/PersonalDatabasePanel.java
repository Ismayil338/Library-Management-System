package dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PersonalDatabasePanel extends JPanel {
    private final DefaultTableModel model;

    public PersonalDatabasePanel(DefaultTableModel model) {
        this.model = model;
        setLayout(new BorderLayout());

        JTable dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getRoot(this);
            GeneralDatabaseDialog dialog = new GeneralDatabaseDialog(parentFrame);
            dialog.setVisible(true);

            DefaultTableModel generalModel = dialog.getModel();
            JTable generalTable = new JTable(generalModel);
            JScrollPane generalScrollPane = new JScrollPane(generalTable);

            JOptionPane.showMessageDialog(parentFrame, generalScrollPane, "General Database", JOptionPane.PLAIN_MESSAGE);

            int selectedRow = generalTable.getSelectedRow();
            if (selectedRow != -1) {
                Object[] rowData = new Object[generalModel.getColumnCount()];
                for (int i = 0; i < generalModel.getColumnCount(); i++) {
                    rowData[i] = generalModel.getValueAt(selectedRow, i);
                }
                model.addRow(rowData);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = dataTable.getSelectedRow(); // Get selected row index
            if (selectedRow != -1) {
                model.removeRow(selectedRow); // Remove selected row from personal database
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            if (!searchText.isEmpty()) {
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
                dataTable.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            } else {
                dataTable.setRowSorter(null);
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }

            private void filterTable() {
                String searchText = searchField.getText().trim().toLowerCase();
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
                dataTable.setRowSorter(sorter);
                if (!searchText.isEmpty()) {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                } else {
                    dataTable.setRowSorter(null);
                }
            }
        });
    }

    public void loadPersonalCsvData() {
        String filePath = "userdatabases/" + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
