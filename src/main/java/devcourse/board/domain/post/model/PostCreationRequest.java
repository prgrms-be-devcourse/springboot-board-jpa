package devcourse.board.domain.post.model;

public record PostCreationRequest(
        Long memberId,
        String title,
        String content
) {
}
