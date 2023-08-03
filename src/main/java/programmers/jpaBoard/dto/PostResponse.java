package programmers.jpaBoard.dto;

import lombok.Builder;
import lombok.Getter;
import programmers.jpaBoard.entity.User;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private User user;

    private LocalDateTime createdAt;

    @Builder
    public PostResponse(Long id, String title, String content, User user, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}
