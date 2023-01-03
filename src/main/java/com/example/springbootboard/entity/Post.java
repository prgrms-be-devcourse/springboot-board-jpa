package com.example.springbootboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }

    @Builder
    public Post(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setUser(User user){
        if(this.user != user){
            this.user.getPostList().remove(this);
        }
        this.user = user;
        user.getPostList().add(this);
    }
}
