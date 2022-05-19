package com.example.spring_jpa_post.post.entity;

import com.example.spring_jpa_post.BaseEntity;
import com.example.spring_jpa_post.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Post(String title, String content,User user) {
        validateContent(content);
        validateTitle(title);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changContent(String content) {
        this.content = content;
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException(MessageFormat.format("Title is not correct, Title is => {0}", title));
    }

    private void validateContent(String content) {
        if (content == null || content.isBlank())
            throw new IllegalArgumentException(MessageFormat.format("Content is not correct, Content is => {0}", content));
    }
}
