package com.example.springbootjpa.domain.post;

import com.example.springbootjpa.domain.BaseEntity;
import com.example.springbootjpa.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "POSTS_TBL")
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {}

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.setCreatedBy(user.getName());
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
