package dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GeneralDatabasePanel extends JPanel {
    public final DefaultTableModel model;
    private JTable dataTable;

    public GeneralDatabasePanel(DefaultTableModel model, boolean isAdmin) {
        this.model = model;
        setLayout(new BorderLayout());

        dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        
        if (isAdmin) {
            JButton addButton = new JButton("Add");
            buttonPanel.add(addButton);
            addButton.addActionListener(e -> addData());

            JButton deleteButton = new JButton("Delete");
            buttonPanel.add(deleteButton);
            deleteButton.addActionListener(e -> deleteSelectedRow());

            JButton updateButton = new JButton("Update");
            buttonPanel.add(updateButton);
            updateButton.addActionListener(e -> updateSelectedRow());
        }

        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.NORTH);

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

    public void loadCSVData() {
        try (BufferedReader br = new BufferedReader(new FileReader("generaldatabase/generaldatabase.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(",")) {
                    String title = line.substring(0, line.length() - 1).trim();
                    model.addRow(new Object[]{removeQuotationMarks(title), "No Author", "No Review", "No Rating"});
                } else if (line.startsWith(",")) {
                    String author = line.substring(1).trim();
                    model.addRow(new Object[]{"No Title", author, "No Review", "No Rating"});
                } else {
                    String[] data = line.split(",");
                    String author = data[data.length - 1].trim();
                    if (author.isEmpty()) {
                        author = "No Author";
                    }
                    for (int i = 0; i < data.length - 1; i++) {
                        String title = data[i].trim();
                        if (title.isEmpty()) {
                            continue;
                        }
                        model.addRow(new Object[]{removeQuotationMarks(title), author, "No Review", "No Rating"});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveCSVData();
    }

    public void loadCSVDataAfterDatabaseCreated() {
        try (BufferedReader br = new BufferedReader(new FileReader("generaldatabase/generaldatabase.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSelectedRow() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String[] rowData = new String[dataTable.getColumnCount()];
        for (int i = 0; i < rowData.length; i++) {
            Object value = dataTable.getValueAt(selectedRow, i);
            rowData[i] = value != null ? value.toString() : "";
        }
    
        JTextField[] textFields = new JTextField[rowData.length];
        for (int i = 0; i < 2; i++) {
            textFields[i] = new JTextField(rowData[i]);
        }
    
        int option = JOptionPane.showConfirmDialog(this, textFields, "Edit Row", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (int i = 0; i < 2; i++) {
                model.setValueAt(textFields[i].getText(), selectedRow, i);
            }
    
            // Save changes to the CSV file
            saveCSVData();
        }
    }

    private void saveCSVData() {
        try (FileWriter writer = new FileWriter("generaldatabase/generaldatabase.csv")) {
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    String value = model.getValueAt(i, j).toString();
                    writer.append(value);
                    if (j < model.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data to CSV file.", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    } 
    
    private String removeQuotationMarks(String s) {
        return s.replace("\"", "");
    }

    public void addData() {
        JTextField titleField = new JTextField(10);
        JTextField authorField = new JTextField(10);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);

        int option = JOptionPane.showConfirmDialog(this, inputPanel, "Add New Entry", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            if (!title.isEmpty() && !author.isEmpty()) {
                model.addRow(new Object[]{title, author, "No Review", "No Rating"});
                saveCSVData();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both title and author.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void deleteSelectedRow() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmDelete = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirmDelete == JOptionPane.YES_OPTION) {
            model.removeRow(selectedRow);
            saveCSVData();
        }
    }
}
