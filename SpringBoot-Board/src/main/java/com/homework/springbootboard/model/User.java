package com.homework.springbootboard.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please type name")
    private String name;

    @NotNull(message = "Please type age")
    private int age;

    @Pattern(regexp = "[a-zA-Z]",message = "Hobby should be written by English")
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    public void addPost(Post post){
        post.setUser(this);
    }
}
