package com.kdt.boardMission.domain;

import com.kdt.boardMission.domain.superClass.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(User user, String title, String content) {
        this.title = title;
        this.content = content;
        addUser(user);
    }

    public void addUser(User user) {
        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
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
