package com.juwoong.springbootboardjpa.post.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juwoong.springbootboardjpa.common.BaseEntity;
import com.juwoong.springbootboardjpa.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "POSTS")
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    protected Post() {
    }

    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
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
