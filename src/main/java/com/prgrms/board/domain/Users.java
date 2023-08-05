package com.prgrms.board.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Users extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, nullable = false)
    private String email;
    private String name;
    private int age;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
    public Users(String email, String name, int age) {
        validateEmail(email);
        validateName(name);
        validateAge(age);
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public void updateUser(String name, Integer age) {
        if(name == null && age == null) {
            throw new IllegalArgumentException("업데이트 요청 형식이 올바르지 않습니다.");
        }

        if(!name.isEmpty()) {
            validateName(name);
            this.name = name;
        }
        if (Objects.nonNull(age)) {
            validateAge(age);
            this.age = age;
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일 값이 비어있습니다.");
        }
        if (!email.matches(".+@.+")) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름 값이 비어있습니다.");
        }
    }
    private void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("나이는 0보다 커야 합니다.");
        }
    }
}
