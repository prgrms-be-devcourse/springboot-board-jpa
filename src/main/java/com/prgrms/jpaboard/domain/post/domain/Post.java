package com.prgrms.jpaboard.domain.post.domain;

import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.global.common.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {
    }

    public Post(String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, String title, String content) {
        super(createdBy, createdAt, updatedAt);
        this.title = title;
        this.content = content;
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private String title;
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public PostBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Post build() {
            return new Post(createdBy, createdAt, updatedAt, title, content);
        }
    }

    public void updateTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void setUser(User user) {
        if(Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

}
