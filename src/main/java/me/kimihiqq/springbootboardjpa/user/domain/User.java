package me.kimihiqq.springbootboardjpa.user.domain;

import jakarta.persistence.*;
import lombok.*;
import me.kimihiqq.springbootboardjpa.global.domain.BaseEntity;
import me.kimihiqq.springbootboardjpa.global.error.ErrorCode;
import me.kimihiqq.springbootboardjpa.global.exception.UserException;
import me.kimihiqq.springbootboardjpa.post.domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Getter
@Entity
public class User extends BaseEntity {

    private static final Pattern NAME_PATTERN = Pattern.compile("[\\p{L} ]+");

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age, String hobby) {
        validateAge(age);
        validateName(name);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }


    private void validateName(String name) {
        if (name == null || name.isBlank() || !NAME_PATTERN.matcher(name).matches()) {
            throw new UserException(ErrorCode.INVALID_USER_INPUT, String.format("Invalid name: %s", name));
        }
    }

    private void validateAge(int age) {
        if (age < 0 || age > 120) {
            throw new UserException(ErrorCode.INVALID_USER_INPUT, String.format("Invalid age: %d", age));
        }
    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    public void removePost(Post post) {
        post.setUser(null);
    }

    public void updateUser(String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}