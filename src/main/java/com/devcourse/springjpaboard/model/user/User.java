package com.devcourse.springjpaboard.model.user;

import com.devcourse.springjpaboard.model.BaseEntity;
import com.devcourse.springjpaboard.model.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        post.setUser(this);
    }
}
