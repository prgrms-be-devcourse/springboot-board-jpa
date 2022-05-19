package com.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        author.getPosts().add(this);
        this.author = author;
        this.setCreatedBy(author.getName());
        this.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
