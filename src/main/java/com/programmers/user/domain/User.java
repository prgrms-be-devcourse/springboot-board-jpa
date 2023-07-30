package com.programmers.user.domain;

import com.programmers.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(length = 100, nullable = false)
    private String hobby;

    protected User() {
    }

    public User(String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        validateHobby(hobby);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private static void validateHobby(String hobby) {
        if (Objects.isNull(hobby) || hobby.isBlank()) {
            throw new IllegalArgumentException("취미가 비어있습니다.");
        }
    }

    private static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException(String.format("나이가 0보다 작습니다. 입력값: %s", age));
        }
    }

    private static void validateName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
    }
}
