package com.example.jpaboard.domain.post;

import com.example.jpaboard.domain.BaseTimeEntity;
import com.example.jpaboard.domain.user.User;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    protected Post() {
    }

    public boolean isSameWriter(User user) {
        return this.user.equals(user);
    }

    public void update(String title, String content) {
        if (title != null || !title.isBlank()) {
            this.title = title;
        }
        if (content != null || !content.isBlank()) {
            this.content = content;
        }
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
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
}


