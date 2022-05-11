package com.study.board.domain.post.domain;

import com.study.board.domain.support.auditing.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static lombok.AccessLevel.PROTECTED;

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

    private Post(Long id, String title, String content) {
        checkNotNull(title, "title - null 이 될 수 없음");
        checkArgument(title.length() <= 255, "title 길이 - 255 이하 여야함");
        checkNotNull(content, "content - null 이 될 수 없음");

        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static Post create(String title, String content){
        return new Post(null, title, content);
    }
}
