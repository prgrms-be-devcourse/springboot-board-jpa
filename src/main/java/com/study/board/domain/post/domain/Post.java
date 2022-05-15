package com.study.board.domain.post.domain;

import com.study.board.domain.support.base.BaseEntity;
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
public class Post extends BaseEntity {

    public static final int POST_TITLE_MAX_LENGTH = 255;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "written_date_time", updatable = false)
    private LocalDateTime writtenDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    private Post(String title, String content, User writer) {
        checkArgument(hasText(title), "title - 글자를 가져야함");
        checkArgument(title.length() <= POST_TITLE_MAX_LENGTH, "title 길이 - " + POST_TITLE_MAX_LENGTH + " 이하 여야함");
        checkArgument(hasText(content), "content - 글자를 가져야함");
        checkNotNull(writer, "writer - null 이 될 수 없음");

        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writtenDateTime = now();
    }

    public static Post create(String title, String content, User writer) {
        return new Post(title, content, writer);
    }

    public Post update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public boolean hasOfWriter(User writer) {
        return writer.getId().equals(this.writer.getId());
    }
}
