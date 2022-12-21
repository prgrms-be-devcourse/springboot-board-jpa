package com.spring.board.springboard.user.domain;

import com.spring.board.springboard.post.domain.Post;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
public class Member {

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

    @OneToMany(mappedBy = "member")
    private List<Post> postList;

    public Member() {
    }

    public Member(String name, Integer age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public List<Post> getPostList() {
        return postList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) &&
                Objects.equals(name, member.name) &&
                Objects.equals(age, member.age) &&
                hobby == member.hobby &&
                Objects.equals(postList, member.postList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, hobby, postList);
    }
}
