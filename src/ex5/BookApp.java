package ex5;

import java.util.ArrayList;
import java.util.List;

public class BookApp {
    static class Book {
        private String title;
        private String author;
        private int yearOfPublication;
        private String publisher;

        public Book(String title, String author, int yearOfPublication) {
            this.title = title;
            this.author = author;
            this.yearOfPublication = yearOfPublication;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPublisher() {
            return publisher;
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
        List<Book> books = new ArrayList<>();
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925));
        books.add(new Book("For Whom the Bell Tolls", "Ernest Hemingway", 1940));
        books.add(new Book("1984", "George Orwell", 1949));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960)); // Not in range

        // Set publisher name for all books using a lambda expression
        books.forEach(book -> book.setPublisher("Classic Books"));

        // Filter and print books published between 1920 and 1950
        books.stream()
                .filter(book -> book.getYearOfPublication() >= 1920 && book.getYearOfPublication() <= 1950)
                .forEach(System.out::println);
    }
}

