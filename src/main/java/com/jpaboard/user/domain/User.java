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

    @Column(name = "name", nullable = false, length = 10)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false, length = 10)
    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    protected User() {
    }

    public User(String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        validateHobby(hobby);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.assignWriter(this);
    }

    private void validateName(String name) {
        if (name.length() > 10) {
            throw new IllegalArgumentException("이름은 10자를 넘을 수 없습니다.");
        }
    }

    private void validateHobby(String hobby) {
        if (hobby.length() > 10) {
            throw new IllegalArgumentException("취미는 10자를 넘을 수 없습니다.");
        }
    }

    private void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("나이는 0세부터 150까지 입니다.");
        }
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
        List<Post> postList = new ArrayList<>();
        postList.addAll(this.postList);

        return postList;
    }
}
