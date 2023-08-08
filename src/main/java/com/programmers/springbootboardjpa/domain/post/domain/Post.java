package com.programmers.springbootboardjpa.domain.post.domain;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.domain.user.domain.User;
import com.programmers.springbootboardjpa.global.error.ErrorCode;
import com.programmers.springbootboardjpa.global.error.exception.InvalidEntityValueException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    private static final int MAXIMUM_TITLE_LENGTH_LIMIT = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = MAXIMUM_TITLE_LENGTH_LIMIT)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content, User user) {
        checkTitle(title);
        checkContent(content);
        checkUser(user);

        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void checkTitle(String title) {
        if (title == null || title.isEmpty() || title.length() > MAXIMUM_TITLE_LENGTH_LIMIT) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_POST_TITLE);
        }
    }

    public void checkContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_POST_CONTENT);
        }
    }

    public void checkUser(User user) {
        if (user == null) {
            throw new InvalidEntityValueException(ErrorCode.INVALID_POST_WRITER);
        }
    }

    public void update(String title, String content) {
        checkTitle(title);
        checkContent(content);

        this.title = title;
        this.content = content;
    }
}
