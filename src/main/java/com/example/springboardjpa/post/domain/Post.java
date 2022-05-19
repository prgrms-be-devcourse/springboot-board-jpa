package com.example.springboardjpa.post.domain;

import com.example.springboardjpa.domain.BaseEntityCreate;
import com.example.springboardjpa.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post extends BaseEntityCreate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name ="title", nullable = false)
    private String title;

    @Column(name ="content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String createBy, LocalDateTime createdAt, String title, String content) {
        super(createBy, createdAt);
        this.title = title;
        this.content = content;
    }

    public void setUser(User user){
        if(Objects.nonNull(this.user)){
            this.user.getPosts().add(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
