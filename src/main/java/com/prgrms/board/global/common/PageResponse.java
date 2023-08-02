package com.prgrms.board.global.common;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
    long totalCount,
    int totalPage,
    int page,
    int size,
    List<T> items
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        System.out.println(page);
        return new PageResponse<>(
            page.getTotalElements(),
            page.getTotalPages(),
            page.getNumber(),
            page.getSize(),
            page.getContent()
        );
    }
}
