package programmers.jpaBoard.dto;

import java.time.LocalDateTime;

public class PostDto {
    public record Request(
            String title,
            String content) {
    }

    public record Response(
            Long id,
            String title,
            String content,
            LocalDateTime createdAt) {
    }
}
