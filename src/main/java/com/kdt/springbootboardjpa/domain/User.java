package com.kdt.springbootboardjpa.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", unique = true, length = 20)
    private String username;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", length = 35)
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    @Builder
    public User(String username, int age, String hobby) {
        this.username = username;
        this.age = age;
        this.hobby = hobby;
    }
}
