package com.example.bookstore.controller;

public class UserRespObj {
    private String Cde;
    private String Msg;
    private String timestamp;

    public UserRespObj(String cde, String msg, String timestamp) {
        Cde = cde;
        Msg = msg;
        this.timestamp = timestamp;
    }

    private UserRespObj(){}

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
}
