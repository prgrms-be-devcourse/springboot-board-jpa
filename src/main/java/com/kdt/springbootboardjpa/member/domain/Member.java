package com.kdt.springbootboardjpa.member.domain;

import com.kdt.springbootboardjpa.global.common.BaseEntity;
import com.kdt.springbootboardjpa.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // jpa entity 객체가 Public, protected 빈 객체를 생성 막기위해서 protected
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull    // bean validation column 씹힘
    @Column(nullable = false)   // not null, @length -> DDL 제약 조건.....
    private String name;

    private int age;

    @Enumerated(value = EnumType.STRING)
    private Hobby hobby;

    @Builder
    public Member(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public void changeMember(String name, int age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
