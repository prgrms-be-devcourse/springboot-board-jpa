package com.example.springbootboardjpa.entity;

import com.example.springbootboardjpa.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

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

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id",referencedColumnName = "id")
    private User user;
}
