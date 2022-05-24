package com.programmers.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends CreatedInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Column(insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        setCreatedAt(LocalDateTime.now());
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            user.getPosts().remove(this);
        }
        this.user = user;
        this.setCreatedBy(user.getName());
        user.getPosts().add(this);
    }
}
