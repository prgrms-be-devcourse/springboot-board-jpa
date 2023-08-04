package prgrms.board.user.application.dto.response;

import prgrms.board.post.domain.Post;

import java.util.List;

public record UserFindResponse(
        String name,
        Integer age,
        String hobby,
        List<Post> posts
) {
}
