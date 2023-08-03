package com.prgms.jpaBoard.domain.post;

import com.prgms.jpaBoard.domain.user.User;
import com.prgms.jpaBoard.global.BaseEntity;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    protected Post() {

    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
    
    public void updatePost(String title, String content) {
        changeTitle(title);
        changeContent(content);
    }

    private void changeTitle(String title) {
        this.title = title;
    }

    private void changeContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
