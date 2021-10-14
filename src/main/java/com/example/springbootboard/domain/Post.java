package com.example.springbootboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Post(String title, String content, User user) {
        super(user.getName(), LocalDateTime.now(), LocalDateTime.now());
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.update();
        this.title = title;
        this.content = content;
    }
}
