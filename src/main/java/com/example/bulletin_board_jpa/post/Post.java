package com.example.bulletin_board_jpa.post;

import com.example.bulletin_board_jpa.BaseEntity;
import com.example.bulletin_board_jpa.user.User;
import jakarta.persistence.*;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 512)
    private String content;

    @ManyToOne
//    @JoinColumn(name = "user_iddd")
    private User user;

    public void setUser(User user) {

        // 기존 user 와의 관계
        if (this.user != null) {
            this.user.getPosts().remove(this); // User -> Post 관계를 삭제
        }
        this.user = user; // Post -> user 단방향 관계
        user.getPosts().add(this); // User -> Post 단방향 관계 추가
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
