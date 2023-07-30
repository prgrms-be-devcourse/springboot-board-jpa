package com.programmers.domain.post.entity;

import com.programmers.domain.base.BaseEntity;
import com.programmers.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Table(name = "posts")
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    private static void validateContent(String content) {
        if (Objects.isNull(content) || content.isBlank()) {
            throw new IllegalArgumentException("본문이 비어있습니다.");
        }
    }

    private static void validateTitle(String title) {
        if (Objects.isNull(title) || title.isBlank()) {
            throw new IllegalArgumentException("제목이 비어있습니다.");
        }
    }

    public void changeTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void changeContent(String content) {
        validateTitle(content);
        this.content = content;
    }
}
