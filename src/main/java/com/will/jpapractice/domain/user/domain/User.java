package com.will.jpapractice.domain.user.domain;

import com.will.jpapractice.global.common.entity.BaseEntity;
import com.will.jpapractice.domain.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private long id;

    @Size(min = 1, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Positive
    @Column(name = "age", nullable = false)
    private int age;

    @OneToMany(mappedBy = "user")
    private final List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

