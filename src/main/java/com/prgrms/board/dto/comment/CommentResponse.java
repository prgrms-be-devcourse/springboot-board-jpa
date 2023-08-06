package com.prgrms.board.dto.comment;

import com.prgrms.board.domain.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CommentResponse {
    private final String content;

    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(comment.getContent());
    }

    public static List<CommentResponse> fromEntities(List<Comment> comments) {
        return comments.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
