package com.homework.springbootboard.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please type name")
    private String name;

    @NotNull(message = "Please type age")
    private int age;

    @Pattern(regexp = "[a-zA-Z]", message = "Hobby should be written by English")
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @Builder
    public User(Long id, String name, int age, String hobby,List<Post> postList){
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postList = postList;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @Builder
    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
