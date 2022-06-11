package com.kdt.board.domain.model;

import com.kdt.board.global.exception.NotValidException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @Column(name = "post_id", updatable = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = 100)
    @NotNull
    private String title;

    @Lob
    @Column(name = "content")
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(Long id, String title, String content, User user) {
        validateTitleAndContent(title, content);
        validateUser(user);

        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    private void validateTitleAndContent(String title, String content) {
        if (title.isEmpty() | content.isEmpty()) {
            throw new NotValidException("title 나 content 가 비어있습니다.");
        }
        if (title.length() > 100) {
            throw new NotValidException("title 의 길이가 제한길이 100을 넘겼습니다.");
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new NotValidException("user 을 찾을 수가 없습니다.");
        }

    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeUser(User user) {
        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }
}
