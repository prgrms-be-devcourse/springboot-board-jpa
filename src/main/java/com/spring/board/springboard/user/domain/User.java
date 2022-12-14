package com.spring.board.springboard.user.domain;

import com.spring.board.springboard.domain.CreateBaseEntity;
import com.spring.board.springboard.post.domain.Post;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User extends CreateBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "hobby")
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public void addPost(Post post) {
        post.setUser(this);
    }
}
