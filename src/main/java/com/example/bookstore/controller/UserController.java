package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add/user")
    public UserRespObj addUser(@RequestBody User user){
        log.info("I am in add user");
        return userService.addUser(user);
    }

    @PostMapping("/login/user")
    public UserRespObj loginUser(@RequestBody User user){
        log.info("I am in login user");
        return userService.loginUser(user);
    }
}
