package prgrms.board.post.application.dto.response;

import java.time.LocalDateTime;

public record PostSaveResponse(
        Long postId,
        Long userId,
        LocalDateTime createdAt
) {
}
