package com.homework.springbootboard.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title",nullable = false,length = 100)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPostList().remove(this);
        }
        this.user = user;
        user.getPostList().add(this);
    }

    public void changeTitle(String title){
        this.title = title;
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void chnageContent(String content){
        this.content = content;
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Builder
    public Post(Long id, String title, String content, User user){
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    @Builder
    public Post(String title, String content,User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
