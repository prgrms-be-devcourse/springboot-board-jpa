package com.example.board.global.dto;

import org.springframework.data.domain.PageImpl;

public record PageableCustom(boolean first,
                             boolean last,
                             boolean hasNext,
                             int totalPages,
                             long totalElements,
                             int page,
                             int size) {

    public static <T> PageableCustom of(PageImpl<T> page) {
        return new PageableCustom(
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getSize());
    }
}
