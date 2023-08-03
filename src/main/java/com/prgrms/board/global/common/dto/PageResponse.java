package com.prgrms.board.global.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record PageResponse<T>(
    long totalCount,
    int totalPage,
    int page,
    int size,
    List<T> items
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
            .totalCount(page.getTotalElements())
            .totalPage(page.getTotalPages())
            .page(page.getNumber())
            .size(page.getSize())
            .items(page.getContent())
            .build();
    }
}
