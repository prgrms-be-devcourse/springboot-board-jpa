package com.jpaboard.user.domain;

import com.jpaboard.entity.BaseEntity;
import com.jpaboard.post.domain.Post;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false, length = 10)
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    protected User() {
    }

    @Builder
    private User(long id, String name, int age, String hobby, List<Post> postList) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postList = postList;
    }

    public void addPost(Post post) {
        post.assignWriter(this);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Post> getPostList() {
        return postList;
    }
}
