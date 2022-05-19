package com.example.springboardjpa.user.domain;

import com.example.springboardjpa.domain.BaseEntityCreate;
import com.example.springboardjpa.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class User extends BaseEntityCreate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name ="name", nullable = false)
    private String name;

    @Column(name ="age", nullable = false)
    private int age;

    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public User(String createBy, LocalDateTime createdAt, String name, int age, String hobby) {
        super(createBy, createdAt);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPosts(Post post){
        post.setUser(this);
    }
}
