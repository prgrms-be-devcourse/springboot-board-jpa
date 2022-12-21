package com.prgms.springbootboardjpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member author;

    public Post(Long postId, String title, String content, Member author) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post(String title, String content, Member author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void setUser(Member member) {
        if (Objects.nonNull(this.author)) {
            member.getPosts().remove(this);
        }
        this.author = member;
        member.getPosts().add(this);
    }
}
