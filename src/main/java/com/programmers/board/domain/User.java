package com.programmers.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;
    private int age;

    @Column(nullable = false, length = 50)
    private String hobby;

    protected User() {
    }

    public User(String name, int age, String hobby, String createdBy) {
        super(createdBy);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}

