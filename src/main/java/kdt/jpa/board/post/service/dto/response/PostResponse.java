package kdt.jpa.board.post.service.dto.response;

public record PostResponse (
        String title,
        String content,
        String userName
) {
}
