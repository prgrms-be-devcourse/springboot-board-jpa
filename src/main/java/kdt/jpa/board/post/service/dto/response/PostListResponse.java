package kdt.jpa.board.post.service.dto.response;

import org.springframework.data.domain.Page;

public record PostListResponse(
    Page<PostResponse> responses
) {

}
