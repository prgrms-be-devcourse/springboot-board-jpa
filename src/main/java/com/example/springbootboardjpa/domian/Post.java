package com.example.springbootboardjpa.domian;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
