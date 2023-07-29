package com.board.server.domain.post.dto.response;

import lombok.Builder;

@Builder
public record PageInfo(
        int totalPageSize,
        int currentPageIndex,
        Boolean isEnd
) {
    public static PageInfo of(int totalPageSize, int currentPageIndex, Boolean isEnd) {
        return PageInfo.builder()
                .totalPageSize(totalPageSize)
                .currentPageIndex(currentPageIndex)
                .isEnd(isEnd)
                .build();
    }
}
