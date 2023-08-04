package com.programmers.springbootboardjpa.domain.user.domain;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.global.error.ErrorCode;
import com.programmers.springbootboardjpa.global.error.exception.InvalidEntityValueException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private static final int MAXIMUM_NAME_LENGTH_LIMIT = 20;
    private static final int MAXIMUM_HOBBY_LENGTH_LIMIT = 30;
    private static final int MINIMUM_AGE_LIMIT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = MAXIMUM_NAME_LENGTH_LIMIT)
    private String name;

    @Column
    private int age;

    @Column(nullable = false, length = MAXIMUM_HOBBY_LENGTH_LIMIT)
    private String hobby;

    public User(String name, int age, String hobby) {
        checkName(name);
        checkAge(age);
        checkHobby(hobby);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void checkName(String name) {
        checkNameLength(name);
        checkCharacterPattern(name);
    }

    private void checkNameLength(String name) {
        if (name == null || name.isEmpty() || name.length() > MAXIMUM_NAME_LENGTH_LIMIT) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_USER_NAME_LENGTH);
        }
    }

    private void checkCharacterPattern(String request) {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", request)) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_USER_NAME_PATTERN);
        }
    }

    private void checkAge(int age) {
        if (age < MINIMUM_AGE_LIMIT) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_USER_AGE);
        }
    }

    private void checkHobby(String hobby) {
        if (hobby == null || hobby.isEmpty() || hobby.length() > MAXIMUM_HOBBY_LENGTH_LIMIT) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_USER_HOBBY);
        }
    }

    public void update(String name, int age, String hobby) {
        checkName(name);
        this.name = name;

        checkAge(age);
        this.age = age;

        checkHobby(hobby);
        this.hobby = hobby;
    }
}
