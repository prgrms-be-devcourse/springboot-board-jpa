package com.example.jpaboard.domain.post;

import com.example.jpaboard.domain.BaseTimeEntity;
import com.example.jpaboard.domain.user.User;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @Column(nullable = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
