package devcource.hihi.boardjpa.dto.post;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import lombok.Getter;


import java.time.LocalDateTime;

public record ResponsePostDto(Long id, String title, String content,
                              Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    public static ResponsePostDto toResponseDto(Post post) {
        return new ResponsePostDto(post.getId(),post.getTitle(), post.getContent(), post.getUser().getId(),post.getCreated_at(),post.getUpdated_at());
    }
}
