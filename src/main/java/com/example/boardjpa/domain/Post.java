package com.example.boardjpa.domain;

import com.example.boardjpa.exception.ErrorCode;
import com.example.boardjpa.exception.custom.FieldBlankException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        checkNullOrValid(title, user);
        this.title = title;
        setContent(content);
        setUser(user);
        this.createdAt = LocalDateTime.now();
        this.createdBy = user.getName();
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void setContent(String content) {
        if (Objects.isNull(content) || content.isBlank()) {
            throw new FieldBlankException("필수 필드가 비어있습니다.", ErrorCode.FIELD_BLANK);
        }
        this.content = content;
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

    public User getUser() {
        return user;
    }

    private void checkNullOrValid(String title, User user) {
        if (Objects.isNull(title) || title.isBlank() || Objects.isNull(user)) {
            throw new FieldBlankException("필수 필드가 비어있습니다.", ErrorCode.FIELD_BLANK);
        }
    }
}
