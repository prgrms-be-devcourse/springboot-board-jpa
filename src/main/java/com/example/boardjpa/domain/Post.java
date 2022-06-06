package com.example.boardjpa.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.boardjpa.util.Validation.checkBlank;
import static com.example.boardjpa.util.Validation.checkNull;

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
        setTitle(title);
        setContent(content);
        setUser(user);
        this.createdAt = LocalDateTime.now();
        this.createdBy = user.getName();
    }

    public void setUser(User user) {
        checkNull(user);
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void setContent(String content) {
        checkNull(content);
        checkBlank(content);
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

    private void setTitle (String title) {
        checkNull(title);
        checkBlank(title);
        this.title = title;
    }
}
