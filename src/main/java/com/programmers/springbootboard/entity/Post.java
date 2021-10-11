package com.programmers.springbootboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"user"})
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
