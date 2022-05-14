package com.kdt.springbootboardjpa.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby", length = 35)
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public User() {
    }
}
