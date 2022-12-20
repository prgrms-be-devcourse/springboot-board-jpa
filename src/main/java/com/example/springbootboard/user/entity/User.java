package com.example.springbootboard.user.entity;

import com.example.springbootboard.audit.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @Column(nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
}
