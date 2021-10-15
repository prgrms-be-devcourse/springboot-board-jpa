package spring.jpa.board.dto.post;

import java.time.LocalDateTime;

public record PostFindRequest(Long id, String title, String content, LocalDateTime createdAt,
                              Long createdBy, Long userId) {

}
