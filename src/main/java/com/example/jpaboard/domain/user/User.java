package com.example.jpaboard.domain.user;

import com.example.jpaboard.domain.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Positive
    private int age;

    private String hobby;

    protected User() {
    }

    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
