package com.kdt.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "comment")
@Builder
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {

    }

    public Long getId() {
        return this.id;
    }

    public String getUserName() {
        return this.user.getName();
    }

    public String getContent() {
        return this.content;
    }

    public User getUser() { return this.user; }

    public void changeContent(String newContent) {
        this.content = newContent;
    }
}
