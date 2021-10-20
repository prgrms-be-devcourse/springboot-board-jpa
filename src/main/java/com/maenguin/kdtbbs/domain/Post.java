package com.maenguin.kdtbbs.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long view;

    @Version
    private Long version;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeUser(User user) {
        if (Objects.nonNull(user)) {
            user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void editPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseView() {
        this.view++;
    }
}
