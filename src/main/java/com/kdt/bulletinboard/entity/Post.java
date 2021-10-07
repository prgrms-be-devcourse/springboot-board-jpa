package com.kdt.bulletinboard.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "post")
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    public Post(String title) {
        this.title = title;
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
