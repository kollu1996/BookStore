package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookOrder;
import com.example.bookstore.entity.Order;
import com.example.bookstore.entity.User;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/place/order")
    public OrderResponseObj addBook(@RequestBody List<BookOrder> bookOrder, @AuthenticationPrincipal User userDetails){
        log.info("I am placing an order with user: {}", userDetails.getUsername());
        if(null == userDetails.getUsername()){
            return new OrderResponseObj("200", "Use is null in the token", LocalDateTime.now().toString());
        }
        return orderService.orderBook(bookOrder, userDetails.getUsername());
    }

    @GetMapping("/get/order")
    public OrderResponseObj specificOrder(@RequestParam String bookName, @AuthenticationPrincipal User userDetails){
        log.info("I am placing an order with user: {}", userDetails.getUsername());
        if(null == userDetails.getUsername()){
            return new OrderResponseObj("200", "Use is null in the token", LocalDateTime.now().toString());
        }
        return orderService.getOrderDetails(bookName, userDetails.getUsername());
    }

    @GetMapping("/getall/order")
    public OrderResponseObj allOrders(@AuthenticationPrincipal User userDetails){
        log.info("I am placing an order with user: {}", userDetails.getUsername());
        if(null == userDetails.getUsername()){
            return new OrderResponseObj("200", "Use is null in the token", LocalDateTime.now().toString());
        }
        return orderService.getAllOrders(userDetails.getUsername());
    }

    @PutMapping("/modify/order")
    public OrderResponseObj modifyOrder(@RequestParam String orderId,@RequestParam String status, @AuthenticationPrincipal User userDetails){
        log.info("I am in update books");
        return orderService.updateOrder(orderId, status, userDetails.getUsername());
    }
}
