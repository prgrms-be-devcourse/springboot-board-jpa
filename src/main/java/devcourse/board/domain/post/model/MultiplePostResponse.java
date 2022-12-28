package devcourse.board.domain.post.model;

import java.util.List;

public record MultiplePostResponse(
        List<PostResponse> postResponses
) {
}
