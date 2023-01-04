package devcourse.board.domain.post.model;

public record SimplePostResponse(
        Long postId,
        String title
) {

    public SimplePostResponse(Post post) {
        this(post.getId(), post.getTitle());
    }
}
