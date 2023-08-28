package com.example.board.dto.comment;

import com.example.board.domain.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {
    //게시글에서 댓글을 가져오는 과정에서 무한 참조 발생으로 인한 Dto 반환
    private String content;
    private String name;
    private LocalDateTime createdAt;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .content(comment.getContent())
                .name(comment.getUser().getName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
