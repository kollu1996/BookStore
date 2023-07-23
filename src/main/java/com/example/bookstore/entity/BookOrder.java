package com.example.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BOOKORDER")
public class BookOrder {
    @Column(name="BOOKNAME")
    private String bookName;
    @Column(name="QUANTITY")
    private int quantity;
    @Id
    @Column(name="ORDERID")
    private int orderId;

    @Column(name="STATUS")
    String status;
    @Column(name="USER")
    String user;
}
