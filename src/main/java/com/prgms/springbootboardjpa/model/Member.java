package com.prgms.springbootboardjpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long memberId;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(name = "hobby", length = 10)
    private String hobby;

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    public Member(Long memberId, String name, int age, String hobby) {
        this.memberId = memberId;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Member(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void addPosts(Post post) {
        post.setUser(this);
    }
}