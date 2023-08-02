package com.jpaboard.post.domain;

import com.jpaboard.entity.BaseEntity;
import com.jpaboard.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Objects;

@Builder
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {}

    private Post(long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void assignWriter(User user) {
        if(Objects.nonNull(this.user)) {
            this.user.getPostList().remove(this);
        }

        this.user = user;
        user.getPostList().add(this);
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
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
