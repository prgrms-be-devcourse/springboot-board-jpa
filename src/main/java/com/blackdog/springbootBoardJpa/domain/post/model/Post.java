package com.blackdog.springbootBoardJpa.domain.post.model;

import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostUpdateRequest;
import com.blackdog.springbootBoardJpa.domain.user.model.User;
import com.blackdog.springbootBoardJpa.global.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "id", nullable = false)
    private User user;

    protected Post() {
    }

    public static Post builder() {
        return new Post();
    }

    public Post title(String title) {
        this.title = title;
        return this;
    }

    public Post content(String content) {
        this.content = content;
        return this;
    }

    public Post user(User user) {
        this.user = user;
        return this;
    }
    
    public Post build() {
        return new Post(
                this.title,
                this.content,
                this.user
        );
    }

    private Post(
            String title,
            String content,
            User user
    ) {
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

    private void changeTitle(String title) {
        this.title = title;
    }

    private void changeContent(String content) {
        this.content = content;
    }

    public void changePost(PostUpdateRequest post) {
        changeTitle(post.title());
        changeContent(post.content());
    }
}
