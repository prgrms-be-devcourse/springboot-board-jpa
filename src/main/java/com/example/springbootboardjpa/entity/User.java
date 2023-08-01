package com.example.springbootboardjpa.entity;

import com.example.springbootboardjpa.enums.Hobby;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false,length = 30)
    private String name;

    @Column(name = "age",nullable = false,length = 30)
    private int age;

    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "user",orphanRemoval = true,fetch = FetchType.LAZY)
    List<Post> posts = new ArrayList<>();
}
