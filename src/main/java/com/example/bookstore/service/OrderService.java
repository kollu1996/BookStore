package com.example.bookstore.service;

import com.example.bookstore.controller.OrderResponseObj;
import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookOrder;

import java.util.List;

public interface OrderService {
    OrderResponseObj orderBook(List<BookOrder> bookOrder, String username);
    OrderResponseObj getOrderDetails(String bookName, String username);
    OrderResponseObj getAllOrders(String username);

    OrderResponseObj updateOrder(String orderId, String status, String username);
}
