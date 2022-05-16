package com.programmers.springbootboardjpa.domain.post;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {}

    public Post(String createdBy, LocalDateTime cratedAt, String title, String content, User user) {
        super(createdBy, cratedAt);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }
}
