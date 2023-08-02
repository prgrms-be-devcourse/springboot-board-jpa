package com.example.springbootboardjpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "post")
@Data
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title",nullable = false,length = 100)
    private String title;

    @Column(name = "content",nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id",referencedColumnName = "id")
    private User user;

    public void updateUser(User user){
        if(Objects.nonNull(this.user)){
            this.user.removePost(this);
        }
        this.user = user;
        user.addPost(this);
    }
}
