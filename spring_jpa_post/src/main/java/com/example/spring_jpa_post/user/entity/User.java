package com.example.spring_jpa_post.user.entity;

import com.example.spring_jpa_post.BaseEntity;
import com.example.spring_jpa_post.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(value = EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age, Hobby hobby) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank() || name.isEmpty() || name.length() > 20)
            throw new IllegalArgumentException(MessageFormat.format("Name is not correct, Name is => {0}", name));
    }

    private void validateAge(int age) {
        if (age <= 0 || age > 200)
            throw new IllegalArgumentException(MessageFormat.format("Age is not correct, Age is => {0}", age));
    }
}
