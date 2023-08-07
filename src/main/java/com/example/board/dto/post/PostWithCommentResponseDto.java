package com.example.board.dto.post;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Post;
import com.example.board.dto.comment.CommentResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithCommentResponseDto {
    private String title;

    private String content;

    private String name;

    private LocalDateTime createdAt;

    private List<CommentResponseDto> comments;

    public static PostWithCommentResponseDto from(Post post) {
        return PostWithCommentResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .name(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .comments(post.getComments()
                        .stream()
                        .map(CommentResponseDto::from)
                        .toList())
                .build();
    }
}
