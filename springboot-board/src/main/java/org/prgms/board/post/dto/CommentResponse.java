package org.prgms.board.post.dto;

import lombok.Getter;
import org.prgms.board.domain.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {
    private Long id;
    private String content;
    private Long author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public CommentResponse(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.author = entity.getWriter().getId();
        this.createdDate = entity.getCreatedDate();
        this.updatedDate = entity.getUpdatedDate();
    }


}
