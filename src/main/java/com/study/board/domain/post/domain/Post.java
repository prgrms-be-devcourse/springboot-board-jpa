package com.study.board.domain.post.domain;

import com.study.board.domain.support.auditing.BaseEntity;
import com.study.board.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

/**
 * 게시글
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    private Post(Long id, String title, String content, User writer) {
        checkArgument(hasText(title), "title - 글자를 가져야함");
        checkArgument(title.length() <= 255, "title 길이 - 255 이하 여야함");
        checkArgument(hasText(content), "content - 글자를 가져야함");
        checkNotNull(writer, "writer - null 이 될 수 없음");

        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static Post create(String title, String content, User writer){
        return new Post(null, title, content, writer);
    }
}
