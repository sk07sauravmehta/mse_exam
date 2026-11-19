
package com.library.librarymanagement;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();

    // Add book
    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return books;
    }

    // Get book by ID
    public Book getBookById(Integer id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    // Update book
    public Book updateBook(Integer id, Book newBook) {
        Book book = getBookById(id);

        if (book != null) {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setCategory(newBook.getCategory());
            book.setAvailableCopies(newBook.getAvailableCopies());
        }

        return book;
    }

    // Delete book
    public boolean deleteBook(Integer id) {
        Book book = getBookById(id);

        if (book != null) {
            books.remove(book);
            return true;
        }

        return false;
    }
}
