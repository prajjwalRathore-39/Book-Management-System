package org.register_login.bookmanagementsystem.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/dashboard")
    public String urlMapping(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()){
            boolean isAdmin = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_ADMIN"));

            boolean isUser = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_USER"));

            if (isAdmin){
                return "redirect:/admin/dashboard";
            }else if(isUser){
                return "redirect:/user/dashboard";
            }
        }
        return "redirect:/login";
    }

//    @GetMapping("/dashboard")
//    public String urlMapping(){
//        return "redirect:/admin/dashboard";
//    }

}
