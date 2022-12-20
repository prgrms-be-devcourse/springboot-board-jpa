package com.prgrms.java.domain;

import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Objects;

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

    public Post() {
    }

    public Post(Long id, String title, String content, User user) {
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

    public void editPost(String title, String content) {
        editTitle(title);
        editContent(content);
    }

    public void editTitle(String title) {
        Assert.hasLength(title, MessageFormat.format("title must not be empty or null. [title]: {0}", title));
        Assert.state(title.length() <= 30, MessageFormat.format("title must be less than or equal to 30 characters. [title]: {0}", title));
        this.title = title;
    }

    public void editContent(String content) {
        Assert.hasLength(content, MessageFormat.format("content must not be empty or null. [content]: {0}", content));
        this.content = content;
    }

    public void assignUser(User user) {
        this.user = user;
    }
}
