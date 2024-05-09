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

public class PersonalDatabasePanel extends JPanel {
    public final DefaultTableModel model;

    public PersonalDatabasePanel(DefaultTableModel model, String username) {
        this.model = model;
        setLayout(new BorderLayout());

        JTable dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton endButton = new JButton("End");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        buttonPanel.add(startButton);
        buttonPanel.add(endButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);

        add(buttonPanel, BorderLayout.NORTH);

        refreshButton.addActionListener(e -> {
            loadPersonalCsvDataAfterRefresh(username);
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmDelete = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirmDelete == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
                savePersonalCSVData(username);
            }
        });

        startButton.addActionListener(e -> {

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
        initializeSorting(dataTable);
    }

    public void loadPersonalCsvData(String username) {
        String filePath = "userdatabases/" + username + ".csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Skip adding the first column (ID) to the table model
                if (data.length > 1) {
                    String[] rowData = new String[data.length - 1];
                    System.arraycopy(data, 1, rowData, 0, data.length - 1);
                    model.addRow(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPersonalCsvDataAfterRefresh(String username) {
        String filePath = "userdatabases/" + username + ".csv";
        // Clear existing data from the table
        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Skip adding the first column (ID) to the table model
                if (data.length > 1) {
                    String[] rowData = new String[data.length - 1];
                    System.arraycopy(data, 1, rowData, 0, data.length - 1);
                    model.addRow(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePersonalCSVData(String username) {
        String filePath = "userdatabases/" + username + ".csv";
        try (FileWriter writer = new FileWriter(filePath)) {
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

    private void initializeSorting(JTable dataTable) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        dataTable.setRowSorter(sorter);

        sorter.setComparator(0, (Object o1, Object o2) -> {
            String title1 = o1.toString();
            String title2 = o2.toString();
            return title1.compareToIgnoreCase(title2);
        });

        sorter.setComparator(1, (Object o1, Object o2) -> {
            String author1 = o1.toString();
            String author2 = o2.toString();
            return author1.compareToIgnoreCase(author2);
        });

        sorter.setComparator(2, (Object o1, Object o2) -> {
            String review1 = o1.toString();
            String review2 = o2.toString();
            return review1.compareToIgnoreCase(review2);
        });

        sorter.setComparator(3, (Object o1, Object o2) -> {
            Integer rating1 = Integer.parseInt(o1.toString());
            Integer rating2 = Integer.parseInt(o2.toString());
            return rating1.compareTo(rating2);
        });

        sorter.setComparator(4, (Object o1, Object o2) -> {
            String timeSpent1 = o1.toString();
            String timeSpent2 = o2.toString();
            return timeSpent1.compareToIgnoreCase(timeSpent2);
        });

        sorter.setComparator(5, (Object o1, Object o2) -> {
            String startDate1 = o1.toString();
            String startDate2 = o2.toString();
            return startDate1.compareTo(startDate2);
        });

        sorter.setComparator(6, (Object o1, Object o2) -> {
            String endDate1 = o1.toString();
            String endDate2 = o2.toString();
            return endDate1.compareTo(endDate2);
        });

        sorter.setComparator(7, (Object o1, Object o2) -> {
            String userReview1 = o1.toString();
            String userReview2 = o2.toString();
            return userReview1.compareToIgnoreCase(userReview2);
        });

        sorter.setComparator(8, (Object o1, Object o2) -> {
            Integer userRating1 = Integer.parseInt(o1.toString());
            Integer userRating2 = Integer.parseInt(o2.toString());
            return userRating1.compareTo(userRating2);
        });
    }
}
