package me.kimihiqq.springbootboardjpa.post.domain;

import jakarta.persistence.*;
import lombok.*;
import me.kimihiqq.springbootboardjpa.global.domain.BaseEntity;
import me.kimihiqq.springbootboardjpa.global.error.ErrorCode;
import me.kimihiqq.springbootboardjpa.global.exception.PostException;
import me.kimihiqq.springbootboardjpa.user.domain.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
        setUser(user);
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new PostException(ErrorCode.INVALID_POST_INPUT, String.format("Invalid title: %s", title));
        }
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank()) {
            throw new PostException(ErrorCode.INVALID_POST_INPUT, String.format("Invalid content: %s", content));
        }
    }

    public void updatePost(String title, String content) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }
}