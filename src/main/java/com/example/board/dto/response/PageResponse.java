package com.example.board.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(Integer totalPages, Integer currentPage, Integer pageSize, List<T> content) {

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getTotalPages(),
                page.getPageable().getPageNumber() + 1,
                page.getPageable().getPageSize(),
                page.getContent()
        );
    }
}
