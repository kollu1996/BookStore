package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookOrder;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ResponseObj {
    private String Cde;
    private String Msg;
    private String timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Book> bookList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BookOrder> bookOrderList;

    public ResponseObj(String Cde, String Msg, String timestamp) {
        this.Cde = Cde;
        this.Msg = Msg;
        this.timestamp = timestamp;
    }

    private ResponseObj(){

    }

    public ResponseObj(String cde, String msg, String timestamp, List<Book> bookList) {
        Cde = cde;
        Msg = msg;
        this.timestamp = timestamp;
        this.bookList = bookList;
    }

    /*public ResponseObj(String cde, String msg, String timestamp, List<BookOrder> bookOrderList) {
        Cde = cde;
        Msg = msg;
        this.timestamp = timestamp;
        this.bookOrderList = bookOrderList;
    }*/

    public String getCde() {
        return Cde;
    }

    public void setBookOrderList(List<BookOrder> bookOrderList) {
        this.bookOrderList = bookOrderList;
    }

    public void setCde(String Cde) {
        this.Cde = Cde;
    }

    public List<BookOrder> getBookOrderList() {
        return bookOrderList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
