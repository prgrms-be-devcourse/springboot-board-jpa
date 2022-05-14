package com.kdt.springbootboardjpa.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user){
        this.user = user;
        user.addPost(this);
    }

    public Post() {
    }
}
