package com.example.springbootboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post")
@Entity
public class Post extends BaseEntity {



    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Embedded
    private Title title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    private Post(String createdBy, LocalDateTime createdAt, Title title, String content, User user) {
        super(createdBy, createdAt);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static Post createPost(Title title, String content, User user) {
        validate(title, content, user);

        Post post = new Post(user.getName(), LocalDateTime.now(), title, content, user);

        post.setUser(user);

        return post;
    }

    //== 연관관계 편의 메서드 ==//
    private void setUser(User user) {
        this.user = user;
    }

    //== 비즈니스 로직 ==//
    public void update(Title title, String content) {
        validTitle(title);
        validContent(content);

        this.title = title;
        this.content = content;
    }

    private static void validate(Title title, String content, User user) {
        validTitle(title);
        validContent(content);
        validUser(user);
    }

    private static void validUser(User user) {
        Assert.notNull(user, "User should not be null");
    }

    private static void validContent(String content) {
        Assert.notNull(content, "Content should not be null");
    }

    private static void validTitle(Title title) {
        Assert.notNull(title, "Title should not be null");

        if (title.isLongerThanMaxLength()) {
            throw new IllegalArgumentException(MessageFormat.format("Post title length should be under {0}", Title.TITLE_MAX_LENGTH));
        }

        if (title.isShorterThanMinLength()) {
            throw new IllegalArgumentException(MessageFormat.format("Post title length should be over {0}", Title.TITLE_MIN_LENGTH));
        }
    }
}
