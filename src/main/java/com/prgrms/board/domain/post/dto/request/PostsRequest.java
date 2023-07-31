package com.prgrms.board.domain.post.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.validation.constraints.PositiveOrZero;

public record PostsRequest(
    @PositiveOrZero(message = "페이지는 양수여야 합니다.")
    int page,

    @PositiveOrZero(message = "한 페이지 당 게시물의 개수는 양수여야 합니다.")
    int rowsPerPage
) {
    public Pageable toPageable() {
        return PageRequest.of(page, rowsPerPage);
    }
}
