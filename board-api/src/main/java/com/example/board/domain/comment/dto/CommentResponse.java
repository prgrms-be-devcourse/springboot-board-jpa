package com.example.board.domain.comment.dto;

import com.example.board.domain.comment.entity.Comment;
import com.example.board.global.util.CommentUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Long commentId;
    private Long postId;
    private String content;
    private String writer;
    private Long parentId;
    private String createdAt;
    private String updatedAt;
    private String updatedBy;
    private List<CommentResponse> children;

    public void setChildren(List<CommentResponse> children) {
        this.children = children;
    }

    public static List<CommentResponse> toDtoList(List<Comment> flatComments) {
        return CommentUtil.toHierarchyComments(
            flatComments.stream()
                .map(CommentResponse::from)
                .toList()
        );
    }

    public static CommentResponse toDto(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getPost().getId(),
            comment.getContent(),
            comment.getWriter().getName(),
            comment.getParent() != null ? comment.getParent().getId() : null,
            comment.getCreatedAt() == null ? LocalDateTime.now().format(FORMATTER_YYYY_MM_DD) : comment.getCreatedAt().format(FORMATTER_YYYY_MM_DD),
            comment.getUpdatedAt() == null ? LocalDateTime.now().format(FORMATTER_YYYY_MM_DD) : comment.getUpdatedAt().format(FORMATTER_YYYY_MM_DD),
            comment.getUpdatedBy(),
            new ArrayList<>()
        );
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getWriter().getIsDeleted() ? "(알 수 없음)" : comment.getWriter().getName(),
                comment.getParent() != null ? comment.getParent().getId() : null,
                comment.getCreatedAt() == null ? LocalDateTime.now().format(FORMATTER_YYYY_MM_DD) : comment.getCreatedAt().format(FORMATTER_YYYY_MM_DD),
                comment.getUpdatedAt() == null ? LocalDateTime.now().format(FORMATTER_YYYY_MM_DD) : comment.getUpdatedAt().format(FORMATTER_YYYY_MM_DD),
                comment.getWriter().getIsDeleted() ? "(알 수 없음)" : comment.getUpdatedBy(),
                new ArrayList<>()
            );
    }
}
