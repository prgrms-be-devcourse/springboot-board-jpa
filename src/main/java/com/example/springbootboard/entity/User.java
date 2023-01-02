package com.example.springbootboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @Builder
    public User(String name, int age, Hobby hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        setCreatedAt(LocalDateTime.now());
    }

    public void changeHobby(Hobby hobby){
        this.hobby = hobby;
    }
}
