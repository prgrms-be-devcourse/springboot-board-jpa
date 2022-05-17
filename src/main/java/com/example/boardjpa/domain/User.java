package com.example.boardjpa.domain;

import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.FieldBlankException;
import com.example.boardjpa.exception.custom.ValueOutOfRangeException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private Integer age;
    private String hobby;

    @OneToMany(mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();

    protected User() {
    }

    public User(String name, Integer age, String hobby) {
        if (Objects.isNull(name) || Objects.isNull(age)) {
            throw new FieldBlankException("필수 필드가 비어있습니다.", ErrorCode.FIELD_BLANK);
        }
        if (age <= 0 || age > 150) {
            throw new ValueOutOfRangeException("나이 값이 지정 범위를 넘었습니다.", ErrorCode.AGE_OUT_OF_RANGE);
        }
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(name);
    }

    public void addPost(Post post) {
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Post> getPosts() {
        return posts;
    }
}