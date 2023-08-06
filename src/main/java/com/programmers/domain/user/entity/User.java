package com.programmers.domain.user.entity;

import com.programmers.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private static final int MAX_HOBBY_LENGTH = 100;
    private static final int MAX_NAME_LENGTH = 5;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = MAX_NAME_LENGTH, nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(length = MAX_HOBBY_LENGTH, nullable = false)
    private String hobby;

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
        if (hobby.length() > MAX_HOBBY_LENGTH) {
            throw new IllegalArgumentException(String.format("취미 길이가 %s를 넘었습니다. 입력값: %s", MAX_HOBBY_LENGTH, hobby.length()));
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
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("이름 길이가 %s를 넘었습니다. 입력값: %s", MAX_NAME_LENGTH, name.length()));
        }
    }
}
