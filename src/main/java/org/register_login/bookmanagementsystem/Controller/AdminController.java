package org.register_login.bookmanagementsystem.Controller;

import jdk.internal.org.jline.reader.History;
import org.register_login.bookmanagementsystem.Models.Book;
import org.register_login.bookmanagementsystem.Models.IssuedBook;
import org.register_login.bookmanagementsystem.Models.User;
import org.register_login.bookmanagementsystem.Repository.BookRepository;
import org.register_login.bookmanagementsystem.Repository.IssuedBookRepository;
import org.register_login.bookmanagementsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "admin-Dashboard";
    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/users")
    public String showUsersList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "manage-users"; // your thymeleaf page
    }
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        model.addAttribute("user", user);
        return "edit-user";  // returns your Thymeleaf template
    }
    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser){
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: "+id));

        existingUser.setRole(updatedUser.getRole());
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());


        userRepository.save(existingUser);


        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User Not found with the id : "+id));

        userRepository.delete(existingUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/books")
    public String showBookPage(Model model){
        model.addAttribute("books", bookRepository.findAll());
       return "manage-books";
    }

    @GetMapping("/issue-book")
    public String showIssueBookPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());

        // Add "today" for the template
        // model.addAttribute("today", LocalDate.now());

        return "issue-book";
    }

    public AdminController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/issue-book")
    public String issueBook(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issueDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        if (book.getAvailableStock() <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Book is not available right now.");
            return "redirect:/admin/issue-book"; // back to issue page
        }

        // Create IssuedBook
        IssuedBook issuedBook = IssuedBook.builder()
                .user(user)
                .book(book)
                .issueDate(issueDate)
                .dueDate(dueDate)
                .status("Issued")
                .fine(0)
                .build();


        @Autowired
        IssuedBookRepository issuedBookRepository;



        issuedBookRepository.save(issuedBook);

        // Decrease available stock
        book.setAvailableStock(book.getAvailableStock() - 1);
        bookRepository.save(book);

        redirectAttributes.addFlashAttribute("successMessage", "Book issued successfully!");
        return "redirect:/issued-books"; // or redirect wherever you want
    }

}
