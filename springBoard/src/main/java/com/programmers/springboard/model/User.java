package com.programmers.springboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false)
    private String hobby;


    @Builder
    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedAt(LocalDateTime.now());
    }

}
