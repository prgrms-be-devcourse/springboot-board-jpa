package com.programmers.domain.post.entity;

import com.programmers.domain.base.BaseEntity;
import com.programmers.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    private static final int MAX_LENGTH = 50;

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
        if (title.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("제목 길이가 최대 길이 %s 자를 넘었습니다.", MAX_LENGTH));
        }
    }

    public void changeTitleAndContent(String title, String content) {
        validateTitle(title);
        validateTitle(content);
        this.title = title;
        this.content = content;
    }
}
