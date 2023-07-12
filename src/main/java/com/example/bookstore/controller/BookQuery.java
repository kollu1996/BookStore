package com.example.bookstore.controller;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookQuery {
    String title;
    String author;
    String genre;
}
