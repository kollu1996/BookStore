package com.example.bookstore.service;

import com.example.bookstore.controller.BookQuery;
import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    ResponseObj save(Book book);
    ResponseObj getAll();

    ResponseObj getBookByTitle(Book book);
    ResponseObj getBookByAuthor(Book book);
    ResponseObj getBookByGenre(Book book);

    ResponseObj updateBook(Book book);

    ResponseObj deleteBook(String title);
}
