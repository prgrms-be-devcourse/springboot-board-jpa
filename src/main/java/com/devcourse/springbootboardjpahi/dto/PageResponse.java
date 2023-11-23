package com.devcourse.springbootboardjpahi.dto;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record PageResponse<T>(
        Boolean isEmpty,
        Integer totalPages,
        Long totalElements,
        List<T> content
) {

    public static <E> PageResponse<E> from(Page<E> page) {
        return PageResponse.<E>builder()
                .isEmpty(page.isEmpty())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .content(page.getContent())
                .build();
    }
}
