package com.prgrms.springbootboardjpa.member.domain;

import com.prgrms.springbootboardjpa.common.entity.BaseTimeEntity;
import com.prgrms.springbootboardjpa.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;
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
    private List<Post> posts;

    private String name;
    private int age;
    private String hobby;

    public Member() {
    }

    public Member(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}

