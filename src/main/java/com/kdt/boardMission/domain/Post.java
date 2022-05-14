package com.kdt.boardMission.domain;

import com.kdt.boardMission.domain.superClass.Created;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends Created {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addUser(User user) {
        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public static Post createPost(User user, String title, String content) {
        Post post = new Post(title, content);
        post.addUser(user);
        return post;
    }

    public void deletePost() {
        this.user.getPosts().remove(this);
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
