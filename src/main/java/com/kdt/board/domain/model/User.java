package com.kdt.board.domain.model;

import com.kdt.board.global.exception.NotValidException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id", updatable = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 30)
    @NotNull
    private String name;

    @Column(name = "age")
    @NotNull
    private Integer age;

    @Column(name = "hobby", length = 100)
    @NotNull
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(Long id, String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        validateHobby(hobby);

        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateName(String name) {
        if (name.isEmpty() | name.length() > 100) {
            throw new NotValidException("hobby 의 길이는 1이상 100 미만 입니다.");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new NotValidException("age 값을 입력해 주세요");
        }
    }

    private void validateHobby(String hobby) {
        if (hobby.isEmpty() | hobby.length() > 30) {
            throw new NotValidException("name 의 길이는 1이상 30 미만 입니다.");
        }
    }

    public void updateUser(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
