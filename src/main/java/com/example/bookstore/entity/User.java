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
@Table(name="USER")
public class User {
    @Id
    @Column(name="USERNAME")
    private String username;
    @Column(name="PASSWORD")
    private String password;
}
