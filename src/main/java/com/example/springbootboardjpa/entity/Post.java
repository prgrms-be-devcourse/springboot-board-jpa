package com.example.springbootboardjpa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id", referencedColumnName = "id")
    private User user;

    @Builder
    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.removePost(this);
        }
        this.user = user;
        user.addPost(this);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
