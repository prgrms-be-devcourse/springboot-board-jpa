package com.kdt.postproject.domain.post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private int age;
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
    public void addPost(Post post) {
        post.setUser(this);
    }
}
