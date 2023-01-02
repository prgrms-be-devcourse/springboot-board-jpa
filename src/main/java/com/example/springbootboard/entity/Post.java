package com.example.springbootboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
// @Builder
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "content")
    private String content;

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }
}
