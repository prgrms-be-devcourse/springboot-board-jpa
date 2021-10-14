package com.example.springbootboard.domain;

import com.example.springbootboard.domain.vo.Name;
import org.apache.tomcat.jni.Local;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User {

    // Default Constructor
    protected User(){

    }

    public User(Name name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private Name name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = true, length = 30)
    private String hobby;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "createdBy") // 연관관계 주인은 order.createBy
    private List<Post> posts = new ArrayList<>();

    // 연관관계 편의 메소드
    public void addPost(Post post) {
        post.setCreatedBy(this);
    }

    // GETTER
    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    // SETTER

    public void setName(Name name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void setAge(int age) {
        this.age = age;
        this.updatedAt = LocalDateTime.now();
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
        this.updatedAt = LocalDateTime.now();
    }

}
