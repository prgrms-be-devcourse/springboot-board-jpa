package com.example.springboardjpa.post.domain;

import com.example.springboardjpa.domain.BaseEntityCreate;
import com.example.springboardjpa.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

@Getter
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user){
        if(Objects.nonNull(this.user)){
            this.user.getPosts().add(this);
        }
    }
}
