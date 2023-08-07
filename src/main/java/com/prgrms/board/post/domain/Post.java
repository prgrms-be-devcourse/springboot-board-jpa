package com.prgrms.board.post.domain;

import com.prgrms.board.common.entity.BaseEntity;
import jakarta.persistence.*;

@Table(name = "posts")
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", length = 100, nullable = false)
    String title;

    @Column(name = "content", length = 500, nullable = false)
    String content;

    @Column(name = "user_id", nullable = false)
    Long userId;

    protected Post() {
    }

    public Post(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
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
}
