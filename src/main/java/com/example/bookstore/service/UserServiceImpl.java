package com.example.bookstore.service;

import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.controller.UserRespObj;
import com.example.bookstore.entity.User;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public UserRespObj addUser(User user) {
        try{
            User usr = entityManager.find(User.class, user.getUsername());
            String password = user.getPassword();

            // Encrypt the password
            if (!StringUtils.isNotEmpty(password)) {
                return new UserRespObj("200", "Password should not be empty or null",  LocalDateTime.now().toString());
            }
            if(null != usr){
                return new UserRespObj("200", "Username already exists. pls choose different username",  LocalDateTime.now().toString());
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(password));
            entityManager.persist(user);
            return new UserRespObj("200", "USER REGISTRATION SUCCESSFUL",  LocalDateTime.now().toString());
        }
        catch(Exception ex){
            return new UserRespObj("400", "Exception occured" + ex.getMessage(), LocalDateTime.now().toString());
        }
    }
}
