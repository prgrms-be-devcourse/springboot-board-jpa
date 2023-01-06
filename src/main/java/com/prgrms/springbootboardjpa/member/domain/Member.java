package com.prgrms.springbootboardjpa.member.domain;

import com.prgrms.springbootboardjpa.common.BaseTimeEntity;
import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @Id()
    @GeneratedValue
    @Column(name = "member_id")
    private long memberId;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private int age;
    private String hobby;

    public Member() {
    }

    public Member(String email, String password, String name, int age, String hobby) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}

