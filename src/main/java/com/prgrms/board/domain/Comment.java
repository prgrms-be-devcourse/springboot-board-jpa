package com.prgrms.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private String content;

    public Comment(Users user, Post post, String content) {
        validateContent(content);
        this.user = user;
        this.post = post;
        this.content = content;
        List<Comment> comments = post.getComments();
        comments.add(this);
    }

    public void updateComment(String content) {
        if (!content.isEmpty()) {
            validateContent(content);
            this.content = content;
        }
    }

    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("게시글 내용을 입력해주세요.");
        }
    }
}