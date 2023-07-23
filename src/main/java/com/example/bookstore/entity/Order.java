package com.example.bookstore.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ORDER")
public class Order {
    @Id
    int orderId;

    String status;
    String user;
}