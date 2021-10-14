package org.prgms.board.post.dto;

import lombok.Getter;
import org.prgms.board.domain.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private List<CommentResponse> comments;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getWriter().getId();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
        this.comments = entity.getComments().stream()
            .map(CommentResponse::new)
            .collect(Collectors.toList());
    }

}
