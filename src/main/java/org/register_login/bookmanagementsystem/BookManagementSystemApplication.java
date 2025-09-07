package org.register_login.bookmanagementsystem;

import org.register_login.bookmanagementsystem.Models.Role;
import org.register_login.bookmanagementsystem.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookManagementSystemApplication.class, args);
        System.out.println("Book Management System is Running!");
    }

}
