package com.example.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "content")
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
