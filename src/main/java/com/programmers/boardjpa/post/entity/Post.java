package com.programmers.boardjpa.post.entity;

import com.programmers.boardjpa.global.common.BaseEntity;
import com.programmers.boardjpa.post.exception.PostErrorCode;
import com.programmers.boardjpa.post.exception.PostException;
import com.programmers.boardjpa.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    private static final int MIN_TITLE_LEN = 0;
    private static final int MAX_TITLE_LEN = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void changeTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validateTitle(String title) {
        int titleLen = title.length();

        if (titleLen <= MIN_TITLE_LEN || titleLen > MAX_TITLE_LEN) {
            throw new PostException(PostErrorCode.INVALID_TITLE_ERROR);
        }
    }
}
