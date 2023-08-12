package com.example.jpaboard.post.domain;

import com.example.jpaboard.global.BaseEntity;
import com.example.jpaboard.member.domain.Member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Lob
    @NotNull // 3
    @Column(nullable = false)  // 2
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY) // LAZY 반영
    @JoinColumn(name = "member_id") // 흑구멘토님 의견 반영 관계 조건 확실하게
    private Member member;

    protected Post() { }

    Post(String title, String content, Member member) { //
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Post create(String title, String content, Member member) {
        Assert.notNull(title, "not null");
        Assert.notNull(content, "not null");
        Assert.notNull(member, "not null");

        return new Post(title, content, member);
    }

    public boolean isNotOwner(Long memberId) {
        return !Objects.equals(member.getId(), memberId);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }

    public void changTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

}
