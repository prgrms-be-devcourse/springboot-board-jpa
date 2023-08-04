package prgrms.board.post.application.dto.request;


public record PostSaveRequest(
        Long userId,
        String title,
        String content
) {
}
