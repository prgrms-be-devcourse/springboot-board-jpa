package devcource.hihi.boardjpa.dto.post;

import devcource.hihi.boardjpa.domain.User;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public record ResponsePostDto(Long id, String title, String content,
                              User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
