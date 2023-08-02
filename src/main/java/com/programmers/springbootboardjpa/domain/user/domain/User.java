package com.programmers.springbootboardjpa.domain.user.domain;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
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

    @Column(nullable = false, length = 20)
    private String name;

    @Column
    private int age;

    @Column(nullable = false, length = 30)
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
            throw new InvalidEntityValueException("1자 이상 20자 이하로 입력해주세요.");
        }
    }

    private void checkCharacterPattern(String request) {
        if (!Pattern.matches("^[가-힣a-zA-Z]+$", request)) {
            throw new InvalidEntityValueException("한글 또는 영어로 입력해주세요.");
        }
    }

    private void checkAge(int age) {
        if (age < MINIMUM_AGE_LIMIT) {
            throw new InvalidEntityValueException("나이는 0세보다 적을 수 없습니다.");
        }
    }

    private void checkHobby(String hobby) {
        if (hobby == null || hobby.isEmpty() || hobby.length() > MAXIMUM_HOBBY_LENGTH_LIMIT) {
            throw new InvalidEntityValueException("1자 이상 30자 이하로 입력해주세요.");
        }
    }

    public void update(String name, int age, String hobby) {
        checkName(name);
        checkAge(age);
        checkHobby(hobby);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
