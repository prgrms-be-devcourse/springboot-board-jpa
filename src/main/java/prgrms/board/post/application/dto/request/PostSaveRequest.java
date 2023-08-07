package prgrms.board.post.application.dto.request;


import prgrms.board.post.domain.Post;

public record PostSaveRequest(
        Long userId,
        String title,
        String content
) {
    public Post toEntity() {
        return new Post(this.title, this.content);
    }
}
