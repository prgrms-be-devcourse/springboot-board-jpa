package com.example.board.dto.comment;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    @NotNull
    private Long postId;
    @NotNull
    private Long userId;
    @NotBlank(message = "본문은 공백일 수 없습니다.")
    private String content;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }
}
