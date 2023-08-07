package com.prgms.jpaBoard.domain.post;

import com.prgms.jpaBoard.domain.user.User;
import com.prgms.jpaBoard.global.createdUser;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post extends createdUser {

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
        setCreatedBy(user.getName());
    }
    
    public void updatePost(String title, String content) {
        this.title = title;
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
