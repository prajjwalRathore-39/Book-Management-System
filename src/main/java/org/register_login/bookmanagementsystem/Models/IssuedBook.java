package org.register_login.bookmanagementsystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class IssuedBook {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private double fine;

    private String status;

    @PrePersist
    public void prePersist(){
        if (status == null) status = "Issued";
        if (issueDate == null) issueDate = LocalDate.now();
        if (dueDate == null) dueDate = LocalDate.now().plusDays(14);
    }

    public void calculateFine(){
        if (returnDate != null && returnDate.isAfter(dueDate)){
            long overdueDays = ChronoUnit.DAYS.between(dueDate,returnDate);
            this.fine = overdueDays*5; // Rs. 5 per day fine
            this.status = "Overdue";
        }else if(returnDate != null){
            this.fine = 0;
            this.status = "Returned";
        }
    }
}
