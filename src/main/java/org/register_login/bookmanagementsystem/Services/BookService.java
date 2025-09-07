package org.register_login.bookmanagementsystem.Services;

import org.register_login.bookmanagementsystem.Models.Book;
import org.register_login.bookmanagementsystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    // Save or update book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Delete book by ID
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
