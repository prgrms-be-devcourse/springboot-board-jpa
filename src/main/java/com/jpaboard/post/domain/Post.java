package com.jpaboard.post.domain;

import com.jpaboard.entity.BaseEntity;
import com.jpaboard.user.domain.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        this.validateTitle(title);
        this.validateContent(content);

        this.title = title;
        this.content = content;
        this.user = user;
    }

    private void validateTitle(String title) {
        if (title.length() > 30) {
            throw new IllegalArgumentException("제목은 30자를 넘을 수 없습니다.");
        }
    }

    private void validateContent(String content) {
        if (content.length() > 2000) {
            throw new IllegalArgumentException("내용은 2000자를 넘을 수 없습니다.");
        }
    }

    public void updatePost(String title, String content) {
        this.validateTitle(title);
        this.validateContent(content);

        this.title = title;
        this.content = content;
    }

    public void assignWriter(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPostList().remove(this);
        }

        this.user = user;
        user.getPostList().add(this);
    }


    public long getId() {
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
