package ex6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class BookAppUI extends JFrame {
    private List<Book> books = new ArrayList<>();
    private DefaultListModel<String> bookListModel = new DefaultListModel<>();
    private JList<String> bookList = new JList<>(bookListModel);

    public BookAppUI() {
        super("Book Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Text fields and labels for input
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField publisherField = new JTextField();
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Year of Publication:"));
        inputPanel.add(yearField);
        inputPanel.add(new JLabel("Publisher:"));
        inputPanel.add(publisherField);

        // Button to add a book
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener((ActionEvent e) -> {
            int year = Integer.parseInt(yearField.getText().trim());
            Book newBook = new Book(titleField.getText(), authorField.getText(), year, publisherField.getText());
            books.add(newBook);
            bookListModel.addElement(newBook.toString());
            // Clear fields
            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
            publisherField.setText("");
        });

        // List setup
        bookList.setVisibleRowCount(8);
        JScrollPane listScroller = new JScrollPane(bookList);

        // Adding components
        add(inputPanel, BorderLayout.NORTH);
        add(addButton, BorderLayout.SOUTH);
        add(listScroller, BorderLayout.CENTER);

        setVisible(true);
    }

    private static class Book {
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

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", yearOfPublication=" + yearOfPublication +
                    ", publisher='" + publisher + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        // Ensure GUI creation is thread-safe
        SwingUtilities.invokeLater(BookAppUI::new);
    }
}

