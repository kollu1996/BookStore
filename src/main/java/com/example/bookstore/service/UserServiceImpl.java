package com.example.bookstore.service;

import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.controller.UserRespObj;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    private final AuthenticationManager authenticationManager;

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Transactional
    @Override
    public UserRespObj addUser(User user) {
        try {
            User usr = entityManager.find(User.class, user.getUsername());
            String password = user.getPassword();

            // Encrypt the password
            if (!StringUtils.isNotEmpty(password)) {
                return new UserRespObj("200", "Password should not be empty or null", LocalDateTime.now().toString());
            }
            if (null != usr) {
                return new UserRespObj("200", "Username already exists. pls choose different username", LocalDateTime.now().toString());
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(password));
            entityManager.persist(user);
            return new UserRespObj("200", "USER REGISTRATION SUCCESSFUL", LocalDateTime.now().toString());
        } catch (Exception ex) {
            return new UserRespObj("400", "Exception occured" + ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    @Transactional
    @Override
    public UserRespObj loginUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try {
            String username = user.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            TypedQuery<User> theQuery = entityManager.createQuery("from User B WHERE B.username = :username", User.class);
            theQuery.setParameter("username", username);
            List<User> userList = theQuery.getResultList();
            if (userList.size() < 1) {
                return new UserRespObj("200", "NO USER EXISTS", LocalDateTime.now().toString());
            }
            //boolean validUser = passwordEncoder.matches(user.getPassword(), userList.get(0).getPassword());
            log.info("Secret key is: " +secretKey);
            String jwtToken = Jwts.builder()
                    .claim("username", username)
                    .claim("password", user.getPassword())
                    .setSubject(username)
                    .setId(UUID.randomUUID().toString())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return new UserRespObj("200", jwtToken, LocalDateTime.now().toString());

        } catch (Exception ex) {
            return new UserRespObj("400", "Exception occured " + ex.getMessage(), LocalDateTime.now().toString());
        }
    }

    public User getUser(String username){
        try {
            return entityManager.find(User.class, username);
        }
        catch (ServiceException ex){
            log.info("Service exception in fetching user: " + ex.getMessage());
        }
        return new User();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}