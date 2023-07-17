package com.example.bookstore.service;

import com.example.bookstore.controller.UserRespObj;
import com.example.bookstore.entity.User;

public interface UserService {
    public UserRespObj addUser(User usr);

    public UserRespObj loginUser(User user);

    public User getUser(String username);
}
