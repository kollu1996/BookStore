package com.example.bookstore.service;

import com.example.bookstore.controller.BookConstants;
import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookServiceImpl extends BookConstants implements BookService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public ResponseObj save(Book book) {
        try {
            Book bk = entityManager.find(Book.class, book.getTitle());
            if (null != bk) {
                return new ResponseObj("200", FAILED_ADD_BOOK, LocalDateTime.now().toString());
            }
            else {
                entityManager.persist(book);
            }
            return new ResponseObj("200", SUCCESS_ADD_BOOK, LocalDateTime.now().toString());
        } catch (Exception ex) {
            return new ResponseObj("400", ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    @Transactional
    @Override
    public ResponseObj getAll() {
        try{
            TypedQuery<Book> theQuery = entityManager.createQuery("from Book", Book.class);
            List<Book> bookList = theQuery.getResultList();
            return new ResponseObj("200", SUCCESS_ALL_BOOKS, LocalDateTime.now().toString(), bookList);
        }
        catch (Exception ex){
            return new ResponseObj("400", ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    @Transactional
    @Override
    public ResponseObj getBookByTitle(Book book) {
        try{
            String title = book.getTitle();

            TypedQuery<Book> theQuery = entityManager.createQuery("from BOOK B WHERE B.title = :title", Book.class);
            List<Book> bookList = theQuery.getResultList();
            return new ResponseObj("200", SUCCESS_ALL_BOOKS, LocalDateTime.now().toString(), bookList);
        }
        catch (Exception ex){
            return new ResponseObj("400", ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    @Transactional
    @Override
    public ResponseObj getBookByAuthor(Book book) {
        try{
            String author = book.getAuthor();

            TypedQuery<Book> theQuery = entityManager.createQuery("from BOOK B WHERE B.author = :author", Book.class);
            List<Book> bookList = theQuery.getResultList();
            return new ResponseObj("200", SUCCESS_ALL_BOOKS, LocalDateTime.now().toString(), bookList);
        }
        catch (Exception ex){
            return new ResponseObj("400", ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    @Transactional
    @Override
    public ResponseObj getBookByGenre(Book book) {
        try{
            String genre = book.getGenre();

            TypedQuery<Book> theQuery = entityManager.createQuery("from BOOK B WHERE B.genre = :genre", Book.class);
            List<Book> bookList = theQuery.getResultList();
            return new ResponseObj("200", SUCCESS_ALL_BOOKS, LocalDateTime.now().toString(), bookList);
        }
        catch (Exception ex){
            return new ResponseObj("400", ex.getMessage(), LocalDateTime.now().toString());
        }
    }
    @Transactional
    @Override
    public ResponseObj updateBook(Book book){
        try{
            Book bk = entityManager.find(Book.class, book.getTitle());
            if (null == bk) {
                return new ResponseObj("200", FAILED_UPDATE_BOOK_NO_EXISTS, LocalDateTime.now().toString());
            }
            else {
                entityManager.persist(book);
            }
            return new ResponseObj("200", SUCCESSFULLY_UPDATED_BOOK, LocalDateTime.now().toString());
        }
        catch(Exception ex){
            return new ResponseObj("400", FAILED_UPDATE_BOOK + ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    @Transactional
    @Override
    public ResponseObj deleteBook(String title){
        try{
            Book bk = entityManager.find(Book.class, title);
            if (null == bk) {
                return new ResponseObj("200", "Book does not exists to delete", LocalDateTime.now().toString());
            }
            entityManager.remove(bk);
            return new ResponseObj("200", "Successfully deleted book", LocalDateTime.now().toString());
        }
        catch(Exception ex){
            return new ResponseObj("400", "Failed to delete book" + ex.getMessage(), LocalDateTime.now().toString());
        }
    }
}
