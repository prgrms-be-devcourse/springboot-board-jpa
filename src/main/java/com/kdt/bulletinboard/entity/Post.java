package com.kdt.bulletinboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Table(name = "post")
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

    public Post(String title) {
        this.title = title;
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
