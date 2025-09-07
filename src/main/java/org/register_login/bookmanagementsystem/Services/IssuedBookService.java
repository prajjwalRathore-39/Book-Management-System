package org.register_login.bookmanagementsystem.Services;

import org.register_login.bookmanagementsystem.Models.Book;
import org.register_login.bookmanagementsystem.Models.IssuedBook;
import org.register_login.bookmanagementsystem.Models.User;
import org.register_login.bookmanagementsystem.Repository.IssuedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IssuedBookService {

    private final IssuedBookRepository issuedBookRepository;

    @Autowired
    public IssuedBookService(IssuedBookRepository issuedBookRepository) {
        this.issuedBookRepository = issuedBookRepository;
    }

    // Issue a new book to a user
    public IssuedBook issueBook(User user, Book book) {
        Optional<IssuedBook> existing = issuedBookRepository.findByUserAndBookAndStatus(user, book, "Issued");
        if (existing.isPresent()) {
            throw new RuntimeException("This book is already issued to this User!");
        }

        IssuedBook issuedBook = IssuedBook.builder()
                .user(user)
                .book(book)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .status("Issued")
                .fine(0)
                .build();

        return issuedBookRepository.save(issuedBook);
    }

    // Return book
    public IssuedBook returnBook(IssuedBook issuedBook) {
        issuedBook.setReturnDate(LocalDate.now());
        issuedBook.calculateFine();
        issuedBook.setStatus("Returned");
        return issuedBookRepository.save(issuedBook);
    }

    // Get all issued books
    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookRepository.findAll();
    }

    // Get issued books by user
    public List<IssuedBook> getIssuedBooksByUser(User user) {
        return issuedBookRepository.findByUser(user);
    }

    // Get issued book by IssuedBook ID (not userId)
    public IssuedBook getIssuedBookById(Long id) {
        return issuedBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("IssuedBook not found with id: " + id));
    }

//    public IssuedBook issueBook(User user, Book book) {
//        // ✅ Check if the book is already issued to this user
//        Optional<IssuedBook> existing = issuedBookRepository.findByUserAndBookAndStatus(user, book, "Issued");
//        if (existing.isPresent()) {
//            throw new RuntimeException("This book is already issued to this User!");
//        }
//
//        // ✅ Create new issued book
//        IssuedBook issuedBook = IssuedBook.builder()
//                .user(user)
//                .book(book)
//                .issueDate(LocalDate.now())
//                .dueDate(LocalDate.now().plusDays(14)) // default 2 weeks
//                .status("Issued")
//                .fine(0)
//                .build();
//
//        // ✅ Save to DB
//        return issuedBookRepository.save(issuedBook);
//    }
}