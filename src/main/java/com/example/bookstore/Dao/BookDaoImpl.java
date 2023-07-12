package com.example.bookstore.Dao;

import com.example.bookstore.controller.BookConstants;
import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Slf4j
public class BookDaoImpl extends BookConstants implements BookDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Book book) {
        log.info("I will save a book to database");
    }
}
