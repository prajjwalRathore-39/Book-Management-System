package org.register_login.bookmanagementsystem.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(unique = true)
    private String isbn;

    private String genre;

    @Column(length = 1000)
    private String shortDescription;

    private int totalStock;

    private int availableStock;

    @OneToMany(mappedBy = "book")
    private List<IssuedBook> issuedBookList;
}
