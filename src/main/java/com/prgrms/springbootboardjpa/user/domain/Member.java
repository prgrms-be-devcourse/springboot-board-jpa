package com.prgrms.springbootboardjpa.user.domain;

import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
public class Member {
    @Id()
    @GeneratedValue
    @Column(name = "member_id")
    private long memberId;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Post> posts;

    private String name;
    private int age;
    private String hobby;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Member() {
    }

    public Member(String name, int age, String hobby, LocalDateTime createdAt) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdAt = createdAt;
    }
}

