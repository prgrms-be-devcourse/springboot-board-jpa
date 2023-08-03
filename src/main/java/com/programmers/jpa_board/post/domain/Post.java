package com.programmers.jpa_board.post.domain;

import com.programmers.jpa_board.global.BaseEntity;
import com.programmers.jpa_board.user.domain.User;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    private static final String INVALID_TITLE = "제목 형식이 잘못 되었습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content) {
        validateTitleRange(title);
        this.title = title;
        this.content = content;
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

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        setCreatedBy(user.getCreatedBy());
        user.getPosts().add(this);
    }

    private void validateTitleRange(String title) {
        if (title.isEmpty() || title.length() > 100) {
            throw new IllegalArgumentException(INVALID_TITLE);
        }
    }

    public void update(String title, String content) {
        validateTitleRange(title);
        this.title = title;
        this.content = content;
    }
}
