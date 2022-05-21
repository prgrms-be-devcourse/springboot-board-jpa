package com.waterfogsw.springbootboardjpa.post.entity;

import com.waterfogsw.springbootboardjpa.common.entity.BaseEntity;
import com.waterfogsw.springbootboardjpa.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Max(100)
    private String title;

    @NotBlank
    @Lob
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

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

    public Post(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.user = builder.user;
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
