package com.kdt.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Builder
@AllArgsConstructor
public class Post extends BaseEntity {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Post() {

    }

    public User getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUserName() {
        return this.user.getName();
    }

    public String getContent() {
        return this.content;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public Long getId() {
        return this.id;
    }

    public void changePost(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void addComment(Comment newComment) {
        this.comments.add(newComment);
    }

    public void removeComment(Comment comment) { this.comments.remove(comment); }
}
