package com.example.springbootjpa.domain.post;

import com.example.springbootjpa.domain.BaseEntity;
import com.example.springbootjpa.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "POST_TBL")
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 20)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
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
