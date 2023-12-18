package com.example.board.domain.common.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Builder
public record PageResponseDto<T>(
        List<T> contents,
        int page,
        int size,
        long totalCount,
        int start,
        int end,
        boolean prev,
        boolean next,
        Integer prevPage,
        Integer nextPage
) {
    public static <T> PageResponseDto<T> of(Page<T> pagedData) {
        Pageable pageable = pagedData.getPageable();
        int totalPage = pagedData.getTotalPages();

        int pageIndex = pageable.getPageNumber() + 1; // 페이지 번호를 1부터 시작으로 변경
        int pageSize = pageable.getPageSize();

        int tempEnd = (pageIndex + pageSize - 1) / pageSize * pageSize;

        int start = tempEnd - (pageSize - 1);
        int end = Math.min(totalPage, tempEnd);
        boolean hasPrev = start > 1;
        boolean hasNext = totalPage > tempEnd;

        Integer prevPage = pageIndex - 1 <= 0 ? null : pageIndex - 1;
        Integer nextPage = pageIndex + 1 > totalPage ? null : pageIndex + 1;

        return PageResponseDto.<T>builder()
                .contents(pagedData.getContent())
                .page(pageIndex)
                .size(pageSize)
                .totalCount(pagedData.getTotalElements())
                .start(start)
                .end(end)
                .prev(hasPrev)
                .next(hasNext)
                .prevPage(prevPage)
                .nextPage(nextPage)
                .build();
    }
}
