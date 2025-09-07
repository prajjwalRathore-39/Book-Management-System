package org.register_login.bookmanagementsystem.Repository;


import org.register_login.bookmanagementsystem.Models.Book;
import org.register_login.bookmanagementsystem.Models.IssuedBook;
import org.register_login.bookmanagementsystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    List<IssuedBook> findByUserIdAndReturnDateIsNull(Long userId);    // Active Books
    List<IssuedBook> findByDueDateBeforeAndReturnDateIsNull(LocalDate date); // Overdue Books
    Optional<IssuedBook> findByUserAndBookAndStatus(User user, Book book, String status);

    List<IssuedBook> findByUser(User user);
    List<IssuedBook> findByUserId(Long userId);
}
