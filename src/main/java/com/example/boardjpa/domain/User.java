package com.example.boardjpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private Integer age;
    private String hobby;

    @OneToMany(mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(name);
    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
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
}