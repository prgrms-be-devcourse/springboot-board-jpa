package com.example.springbootboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @Column(nullable = false)
    private Long id;
    private String name;
    private int age;
    private Hobby hobby;
}
