package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookOrder;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class OrderResponseObj {

    private String Cde;
    private String Msg;
    private String timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BookOrder> bookOrderList;

    public String getCde() {
        return Cde;
    }

    public void setCde(String cde) {
        Cde = cde;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<BookOrder> getBookOrderList() {
        return bookOrderList;
    }

    public void setBookOrderList(List<BookOrder> bookOrderList) {
        this.bookOrderList = bookOrderList;
    }

    public OrderResponseObj(String cde, String msg, String timestamp, List<BookOrder> bookOrderList) {
        Cde = cde;
        Msg = msg;
        this.timestamp = timestamp;
        this.bookOrderList = bookOrderList;
    }

    public OrderResponseObj(String cde, String msg, String timestamp) {
        Cde = cde;
        Msg = msg;
        this.timestamp = timestamp;
    }
}
