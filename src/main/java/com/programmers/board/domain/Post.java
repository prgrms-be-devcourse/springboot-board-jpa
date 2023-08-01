package com.programmers.board.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Getter
@Entity
public class Post extends BaseEntity {
    private static final Pattern TITLE_PATTERN = Pattern.compile("^.{1,100}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        nullCheck(title, content, user);
        validateTitle(title);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void nullCheck(String title, String content, User user) {
        if (isNull(title)) {
            throw new IllegalArgumentException("게시글 제목은 필수입니다.");
        }
        if (isNull(content)) {
            throw new IllegalArgumentException("게시글 본문은 필수입니다.");
        }
        if (isNull(user)) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }
    }

    public void update(String title, String content) {
        if (nonNull(title)) {
            validateTitle(title);
            this.title = title;
        }
        if (nonNull(content)) {
            this.content = content;
        }
    }

    private void validateTitle(String title) {
        if (invalidTitle(title)) {
            throw new IllegalArgumentException("게시글 제목은 100자 이하여야 합니다");
        }
    }

    private boolean invalidTitle(String title) {
        return !TITLE_PATTERN.matcher(title).matches();
    }
}
