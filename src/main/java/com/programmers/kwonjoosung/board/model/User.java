package com.programmers.kwonjoosung.board.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 20)
    private String hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    public User(String name, Integer age, String hobby) {
        validateName(name);
        validateAge(age);
        validateHobby(hobby);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateHobby(String hobby) {
        if (hobby == null) {
            return;
        }
        Assert.isTrue(hobby.length() <= 20, "취미는 최대 20글자 입니다.");
    }

    private void validateAge(Integer age) {
        Assert.isTrue(age >= 0, "나이는 0살 이상이어야 합니다.");
    }

    private void validateName(String name) {
        Assert.notNull(name, "이름은 필수 값입니다.");
        Assert.isTrue(name.length() > 0 && name.length() <= 20,
                "이름은 최소 1글자 이상, 최대 20글자 이내 입니다.");

    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeAge(Integer age) {
        validateAge(age);
        this.age = age;
    }

    public void changeHobby(String hobby) {
        validateHobby(hobby);
        this.hobby = hobby;
    }
}
