package ex6;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.*;
        import java.awt.event.ActionEvent;

public class BookAppUIWithCustomComponents extends JFrame {
    private BookTableComponent bookTableComponent;
    private InputPanel inputPanel;

    public BookAppUIWithCustomComponents() {
        super("Book Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize book table component
        bookTableComponent = new BookTableComponent();
        inputPanel = createInputPanel();

        // Add book button
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener((ActionEvent e) -> {
            bookTableComponent.addBook(new Book(
                    inputPanel.getText("Title"),
                    inputPanel.getText("Author"),
                    Integer.parseInt(inputPanel.getText("Year of Publication")),
                    inputPanel.getText("Publisher")
            ));
            inputPanel.clearFields();
        });

        // Adding components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(addButton, BorderLayout.SOUTH);
        add(bookTableComponent.getScrollPane(), BorderLayout.CENTER);

        setVisible(true);
    }

    private InputPanel createInputPanel() {
        InputPanel panel = new InputPanel(new String[]{"Title", "Author", "Year of Publication", "Publisher"});
        return panel;
    }

    public static void main(String[] args) {
        // Ensure GUI creation is thread-safe
        SwingUtilities.invokeLater(BookAppUI::new);
    }

    static class BookTableComponent {
        private DefaultTableModel model;
        private JTable table;
        private JScrollPane scrollPane;

        public BookTableComponent() {
            String[] columnNames = {"Title", "Author", "Year of Publication", "Publisher"};
            model = new DefaultTableModel(columnNames, 0);
            table = new JTable(model);
            scrollPane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
            table.setAutoCreateColumnsFromModel(true);
        }

        public void addBook(Book book) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getYearOfPublication(), book.getPublisher()});
        }

        public JScrollPane getScrollPane() {
            return scrollPane;
        }
    }

    static class InputPanel extends JPanel {
        private JTextField titleField = new JTextField(10);
        private JTextField authorField = new JTextField(10);
        private JTextField yearField = new JTextField(10);
        private JTextField publisherField = new JTextField(10);

        public InputPanel(String[] labels) {
            setLayout(new GridLayout(0, 2, 10, 10));
            add(new JLabel("Title:"));
            add(titleField);
            add(new JLabel("Author:"));
            add(authorField);
            add(new JLabel("Year of Publication:"));
            add(yearField);
            add(new JLabel("Publisher:"));
            add(publisherField);
        }

        public String getText(String label) {
            switch (label) {
                case "Title": return titleField.getText();
                case "Author": return authorField.getText();
                case "Year of Publication": return yearField.getText();
                case "Publisher": return publisherField.getText();
                default: throw new IllegalArgumentException("Unknown field: " + label);
            }
        }

        public void clearFields() {
            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
            publisherField.setText("");
        }
    }

    static class Book {
        private String title;
        private String author;
        private int yearOfPublication;
        private String publisher;

        public Book(String title, String author, int yearOfPublication, String publisher) {
            this.title = title;
            this.author = author;
            this.yearOfPublication = yearOfPublication;
            this.publisher = publisher;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public int getYearOfPublication() {
            return yearOfPublication;
        }

        public String getPublisher() {
            return publisher;
        }
    }
}
