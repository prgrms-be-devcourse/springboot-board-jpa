package com.example.board.member;

import com.example.board.BaseEntity;
import com.example.board.post.Post;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;
    private int age;
    private String hobby;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    protected Member() {
    }

    public Member(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.setCreatedBy(name);
        this.setCreatedAt(LocalDateTime.now());
    }
}
