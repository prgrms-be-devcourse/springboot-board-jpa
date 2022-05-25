package com.study.board.domain.post.domain;

import com.study.board.domain.base.BaseIdEntity;
import com.study.board.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseIdEntity {

    public static final int POST_TITLE_MAX_LENGTH = 255;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "written_date_time", updatable = false)
    private LocalDateTime writtenDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    public Post(String title, String content, User writer) {
        checkTitle(title);
        checkContent(content);
        checkWriter(writer);

        this.title = title;
        this.content = content;
        this.writtenDateTime = now();
        this.writer = writer;
    }

    public Post edit(String title, String content) {
        checkTitle(title);
        checkContent(content);

        this.title = title;
        this.content = content;

        return this;
    }

    private void checkTitle(String title) {
        checkArgument(hasText(title), "title - 글자를 가져야함");
        checkArgument(title.length() <= POST_TITLE_MAX_LENGTH, "title 길이 - " + POST_TITLE_MAX_LENGTH + " 이하 여야함");
    }

    private void checkContent(String content) {
        checkArgument(hasText(content), "content - 글자를 가져야함");
    }

    private void checkWriter(User writer) {
        checkNotNull(writer, "writer - null 이 될 수 없음");
    }

    public boolean isWrittenBy(User user) {
        return writer.getId().equals(user.getId());
    }
}
