package com.eunu.springbootboard.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User extends BaseEntity{

    @Id
    @Column(name="id")
    private String userId;

    @Column(name="name", nullable = false, length = 20)
    private String name;

    @Column(name="age", nullable = false)
    private int age;

    @Column(name="hobby", length = 2000)
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public User(String id, String name, int age, String hobby, LocalDateTime time, String createdBy) {
        this.userId = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedAt(time);
        this.setCreatedBy(createdBy);
    }

    public User(String id, String name, int age, String hobby, User user) {
        this.userId = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(user.getUserId());
    }

    public User(String id, String name, int age, User user) {
        this.userId = id;
        this.name = name;
        this.age = age;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(user.getUserId());
    }

    public User(String id, String name, int age, String hobby) {
        this.userId = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedAt(LocalDateTime.now());
    }

    public User(String id, String name, int age) {
        this.userId = id;
        this.name = name;
        this.age = age;
        this.setCreatedAt(LocalDateTime.now());
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
