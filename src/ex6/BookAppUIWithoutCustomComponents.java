package ex6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BookAppUIWithoutCustomComponents extends JFrame {
    private DefaultTableModel tableModel;
    private JTable bookTable;
    private JTextField titleField, authorField, yearField, publisherField;

    public BookAppUIWithoutCustomComponents() {
        super("Book Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Initialize the table model and specify column names
        String[] columnNames = {"Title", "Author", "Year of Publication", "Publisher"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnNames);

        // Setup the table with the model
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        bookTable.setFillsViewportHeight(true);

        // Create input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2)); // 5 rows for fields and labels, 2 columns
        titleField = new JTextField();
        authorField = new JTextField();
        yearField = new JTextField();
        publisherField = new JTextField();

        // Add components to input panel
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Year of Publication:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Publisher:"));
        inputPanel.add(publisherField);

        // Button to add entries to the table
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                String publisher = publisherField.getText();

                // Add row to the table model
                tableModel.addRow(new Object[]{title, author, year, publisher});

                // Clear the input fields
                titleField.setText("");
                authorField.setText("");
                yearField.setText("");
                publisherField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year of Publication must be a valid number.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Layout configuration
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(addButton, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Display the window.
        setVisible(true);
    }

    public static void main(String[] args) {
        // Ensure GUI creation is thread-safe.
        SwingUtilities.invokeLater(() -> new BookAppUI());
    }
}
