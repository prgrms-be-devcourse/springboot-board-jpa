package com.jpaboard.domain.post.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PostPageResponse(
        List<PostResponse> content,
        long numberOfElements,
        long totalElements,
        int pageNumber,
        int pageSize,
        boolean isFirstPage,
        boolean hasNextPage,
        boolean isLastPage
) {
}
