package com.programmers.jpa_board.user.domain;

import com.programmers.jpa_board.global.BaseEntity;
import com.programmers.jpa_board.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.programmers.jpa_board.global.exception.ExceptionMessage.INVALID_AGE;
import static com.programmers.jpa_board.global.exception.ExceptionMessage.INVALID_NAME;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z]+$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false, length = 100)
    private String hobby;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String name, int age, String hobby) {
        validateName(name);
        validateAgeRange(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Post> getPosts() {
        return posts;
    }

    private void validateName(String name) {
        validateNamePattern(name);
        validateNameRange(name);
    }

    private void validateNamePattern(String name) {
        Matcher matcher = NAME_PATTERN.matcher(name);

        if (isValidNameFormat(matcher)) {
            throw new IllegalArgumentException(INVALID_NAME.getMessage());
        }
    }

    private boolean isValidNameFormat(Matcher matcher) {
        return !matcher.find();
    }

    private void validateNameRange(String name) {
        if (isNameWithinRange(name)) {
            throw new IllegalArgumentException(INVALID_NAME.getMessage());
        }
    }

    private boolean isNameWithinRange(String name) {
        return name.isEmpty() || name.length() > 30;
    }

    private void validateAgeRange(int age) {
        if (isWithinAgeRange(age)) {
            throw new IllegalArgumentException(INVALID_AGE.getMessage());
        }
    }

    private boolean isWithinAgeRange(int age) {
        return age < 1 || age > 100;
    }
}
