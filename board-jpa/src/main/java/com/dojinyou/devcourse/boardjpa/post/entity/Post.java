package com.dojinyou.devcourse.boardjpa.post.entity;

import com.dojinyou.devcourse.boardjpa.common.entity.BaseEntity;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Post extends BaseEntity {
    @Id
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 50)
    private String title;

    @NotBlank
    @Lob
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    protected Post() {
    }

    private Post(Builder builder) {
        valid(builder);
        this.title = builder.title;
        this.content = builder.content;
        this.user = builder.user;
    }

    private void valid(Builder builder) {
        if (builder.title == null || builder.title.length() > 50 || builder.title.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (builder.content == null || builder.content.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (builder.user == null || builder.user.getId() == null) {
            throw new IllegalArgumentException();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String content;
        private User user;

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
