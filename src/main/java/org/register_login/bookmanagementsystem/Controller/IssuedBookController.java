package org.register_login.bookmanagementsystem.Controller;


import org.register_login.bookmanagementsystem.Models.Book;
import org.register_login.bookmanagementsystem.Models.IssuedBook;
import org.register_login.bookmanagementsystem.Models.User;
import org.register_login.bookmanagementsystem.Repository.BookRepository;
import org.register_login.bookmanagementsystem.Repository.UserRepository;
import org.register_login.bookmanagementsystem.Services.IssuedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/issued-book")
public class IssuedBookController {

    @Autowired
    private IssuedBookService issuedBookService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public IssuedBookController(IssuedBookService issuedBookService, UserRepository userRepository, BookRepository bookRepository) {
        this.issuedBookService = issuedBookService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/issue")
    public String showIssueForm(Model model){
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "issue-book";
    }

    @PostMapping("/issue")
    public String issueBook(@RequestParam Long userId, @RequestParam Long bookId, Model model){
        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        try{
            issuedBookService.issueBook(user,book);
            model.addAttribute("success", "Book Issued Succesfully");
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("books", bookRepository.findAll());
        return "issue-book";
    }

    @GetMapping("/return")
    public String showReturnPage(Model model){
        List<IssuedBook> issuedBooks = issuedBookService.getAllIssuedBooks();
        model.addAttribute("issuedBooks", issuedBooks);
        return "return-book";
    }

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id) {
        IssuedBook issuedBook = issuedBookService.getIssuedBookById(id);
        issuedBookService.returnBook(issuedBook);  // ✅ Correct
        return "redirect:/user/dashboard";
    }
}
