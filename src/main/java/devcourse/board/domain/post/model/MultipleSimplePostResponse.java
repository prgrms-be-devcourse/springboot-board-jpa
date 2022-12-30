package devcourse.board.domain.post.model;

import java.util.List;

public record MultipleSimplePostResponse(
        List<SimplePostResponse> simplePostResponses
) {
}
