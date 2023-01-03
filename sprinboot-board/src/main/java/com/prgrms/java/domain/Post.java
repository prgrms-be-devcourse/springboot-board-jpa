package com.prgrms.java.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @ManyToOne
    private User user;

    protected Post() {
    }

    public Post(Long id, String title, String content, User user) {
        validate(title, content);
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Post(String title, String content, User user) {
        this(null, title, content, user);
    }

    public Post(String title, String content) {
        this(title, content, null);
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

    public void editPost(String title, String content) {
        editTitle(title);
        editContent(content);
    }

    public void editTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void editContent(String content) {
        validateContent(content);
        this.content = content;
    }

    private void validate(String title, String content) {
        validateTitle(title);
        validateContent(content);
    }

    private void validateContent(String content) {
        Assert.state(StringUtils.hasText(content), "content must be at least one character");
    }

    private void validateTitle(String title) {
        Assert.state(StringUtils.hasText(title), "title must be at least one character");
        Assert.state(title.length() <= 30, "title must be under 31.");
    }

}
