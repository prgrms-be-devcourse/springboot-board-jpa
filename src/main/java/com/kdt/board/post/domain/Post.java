package com.kdt.board.post.domain;

import com.kdt.board.common.entity.BaseEntity;
import com.kdt.board.common.exception.CannotEditPostException;
import com.kdt.board.user.domain.User;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Post() {
    }

    private Post(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.user = builder.user;
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

    public void update(User user, String title, String content) {
        if(!this.user.equals(user)) {
            throw new CannotEditPostException();
        }

        this.title = title;
        this.content = content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String title;
        private String content;
        private User user;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
