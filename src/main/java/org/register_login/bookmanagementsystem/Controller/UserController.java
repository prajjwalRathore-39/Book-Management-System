package org.register_login.bookmanagementsystem.Controller;

import org.register_login.bookmanagementsystem.Models.IssuedBook;
import org.register_login.bookmanagementsystem.Models.User;
import org.register_login.bookmanagementsystem.Repository.UserRepository;
import org.register_login.bookmanagementsystem.Services.IssuedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    IssuedBookService issuedBookService;


    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication authentication){
        String email = authentication.getName();
        User user =  userRepository.findByEmail(email)
                        .orElseThrow(()->new RuntimeException("User not found: "+email));


        model.addAttribute("user", user);
        model.addAttribute("issuedBook", issuedBookService.getIssuedBooksByUser(user));

        return "user-dashboard";
    }

}
