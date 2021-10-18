package kdt.cse0518.board.post.dto;

import kdt.cse0518.board.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    private User user;

    private PostDto() {
    }

    public PostDto update(final String title, final String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
        return this;
    }
}
