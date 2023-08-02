package com.programmers.springbootboardjpa.domain.post.domain;

import com.programmers.springbootboardjpa.domain.BaseEntity;
import com.programmers.springbootboardjpa.domain.user.domain.User;
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

    @Column(nullable = false, length = 50)
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
            throw new InvalidEntityValueException("1자 이상 50자 이하로 입력해주세요.");
        }
    }

    public void checkContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new InvalidEntityValueException("내용은 공백일 수 없습니다.");
        }
    }

    public void checkUser(User user) {
        if (user == null) {
            throw new InvalidEntityValueException("작성자가 포함되어 있어야 합니다.");
        }
    }

    public void update(String title, String content) {
        updateTitle(title);
        updateContent(content);
    }

    public void updateTitle(String title) {
        checkTitle(title);

        this.title = title;
    }

    public void updateContent(String content) {
        checkContent(content);

        this.content = content;
    }
}
