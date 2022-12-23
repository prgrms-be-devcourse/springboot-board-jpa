package com.kdt.springbootboardjpa.domain.member;

import com.kdt.springbootboardjpa.domain.BaseEntity;
import com.kdt.springbootboardjpa.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

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

    public void changeName(String name) {
        this.name = name;
    }
}
