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

public class GeneralDatabasePanel extends JPanel {
    private final DefaultTableModel model;

    public GeneralDatabasePanel(DefaultTableModel model) {
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

        loadCSVData();
    }

    public void loadCSVData() {
        try (BufferedReader br = new BufferedReader(new FileReader("generaldatabase/brodsky.csv"))) {
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
    }

    private String removeQuotationMarks(String s) {
        return s.replace("\"", "");
    }
}
