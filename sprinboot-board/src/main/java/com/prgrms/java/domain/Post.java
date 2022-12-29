package com.prgrms.java.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    @NotNull
    @Size(min = 1, max = 30, message = "title must be over 0 and under 31.")
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    @NotNull
    @Size(min = 1, message = "content must be over 0.")
    private String content;

    @ManyToOne
    private User user;

    protected Post() {
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

    public User getUser() {
        return user;
    }

    public void editPost(String title, String content) {
        editTitle(title);
        editContent(content);
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public void editContent(String content) {
        this.content = content;
    }

}
