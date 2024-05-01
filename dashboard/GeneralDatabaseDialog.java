package dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GeneralDatabaseDialog extends JDialog {
    private DefaultTableModel model;
    public JTable dataTable;
    private int selectedRowIndex = -1;
    private Object[] selectedBookData;

    public GeneralDatabaseDialog(Frame parent) {
        super(parent, "General Database", false);
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        initComponents();
    }

    private void initComponents() {
        model = new DefaultTableModel();
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Review");
        model.addColumn("Rating");

        loadCSVData(model);

        dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            selectedRowIndex = dataTable.getSelectedRow();
            if (selectedRowIndex != -1) {
                selectedBookData = new Object[model.getColumnCount()];
                for (int i = 0; i < model.getColumnCount(); i++) {
                    selectedBookData[i] = model.getValueAt(selectedRowIndex, i);
                }
            }
            dispose();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCSVData(DefaultTableModel model) {
        try (BufferedReader br = new BufferedReader(new FileReader("generaldatabase/generaldatabase.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(",")) {
                    String title = line.substring(0, line.length() - 1).trim();
                    model.addRow(new Object[]{removeQuotationMarks(title), "No Author", "No Review", "No Rating"});
                }
                else if (line.startsWith(",")) {
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

    public DefaultTableModel getModel() {
        return model;
    }

    public Object[] getSelectedBookData() {
        return selectedBookData;
    }
}
