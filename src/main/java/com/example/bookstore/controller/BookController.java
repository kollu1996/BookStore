package com.example.bookstore.controller;

import com.example.bookstore.Dao.BookDaoImpl;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BookController {

    @Autowired
    BookService bookService;

    // Add books to database
    @PostMapping("/add/book")
    public ResponseObj addBook(@RequestBody Book book){
        log.info("I am in add book");
        return bookService.save(book);
    }

    //Get all books in the database
    @GetMapping("/all/books")
    public ResponseObj getBooks(){
        log.info("I am in get books");
        return bookService.getAll();
    }


    //Get one specific book based on author, genre and title
    @GetMapping("/title/book")
    public ResponseObj getBooksByAuthor(@RequestBody Book book){
        log.info("I am in get books with author");
        return bookService.getBookByTitle(book);
    }
    @GetMapping("/author/book")
    public ResponseObj getBooksByTitle(@RequestBody Book book){
        log.info("I am in get books with title");
        return bookService.getBookByTitle(book);
    }
    @GetMapping("/genre/book")
    public ResponseObj getBooksByGenre(@RequestBody Book book){
        log.info("I am in get books with genre");
        return bookService.getBookByTitle(book);
    }

    @PutMapping("/modify/book")
    public ResponseObj UpdateBook(@RequestBody Book book){
        log.info("I am in update books");
        return bookService.updateBook(book);
    }

    @DeleteMapping("/delete/book")
    public ResponseObj DeleteBook(@RequestParam String title){
        log.info("I am in delete books");
        return bookService.deleteBook(title);
    }
}
