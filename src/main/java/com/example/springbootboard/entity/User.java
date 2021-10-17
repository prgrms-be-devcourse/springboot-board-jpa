package com.example.springbootboard.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity<String> {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    @Builder
    public User(@NonNull String name, @NonNull Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        setCreatedBy(name);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeHobby(String hobby) {
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setUser(this);
        post.setCreatedBy(this.getName());
        this.posts.add(post);
    }
}
