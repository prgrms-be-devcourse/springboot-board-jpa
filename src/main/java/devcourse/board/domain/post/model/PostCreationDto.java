package devcourse.board.domain.post.model;

public record PostCreationDto(
        Long memberId,
        String title,
        String content
) {
}
