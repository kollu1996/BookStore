package com.example.bookstore.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BOOK")
public class Book {
    @Id
    @Column(name="TITLE")
   private String title;

    @Column(name="AUTHOR")
    private String author;

    @Column(name="GENRE")
    private String genre;

    @Column(name="PRICE")
    private int price;

}
