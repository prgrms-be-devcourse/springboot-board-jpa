package com.programmers.springbootboard.member.domain;

import com.programmers.springbootboard.common.domain.BaseEntity;
import com.programmers.springbootboard.member.domain.vo.*;
import com.programmers.springbootboard.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Collections;

@Entity
@Builder
@AllArgsConstructor
public class Member extends BaseEntity<Long> {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Embedded
    private Hobby hobby;

    @Embedded
    private Posts posts;

    protected Member() {

    }

    public void update(Name name, Age age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        lastModifiedId(this.getId());
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public Posts getPosts() {
        return posts;
    }
}
