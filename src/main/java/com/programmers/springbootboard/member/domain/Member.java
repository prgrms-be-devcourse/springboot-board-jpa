package com.programmers.springbootboard.member.domain;

import com.programmers.springbootboard.common.domain.BaseEntity;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import com.programmers.springbootboard.post.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

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

    public void update(MemberUpdateRequest request) {
        this.name = new Name(request.getName());
        this.age = new Age(request.getAge());
        this.hobby = new Hobby(request.getHobby());
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
