package com.ray.springbootboard.controller.dto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public record PageResponse<T, E>(
        List<T> data,
        PageableResponse<E> pageData
) {
    public PageResponse(List<T> data, Page<E> page) {
        this(data, new PageableResponse<>(page));
    }

    private record PageableResponse<E>(
            boolean first,
            boolean last,
            int number,
            int size,
            Sort sort,
            int totalPages,
            long totalElements
    ) {

        private PageableResponse(Page<E> page) {
            this(
                    page.isFirst(),
                    page.isLast(),
                    page.getNumber(),
                    page.getSize(),
                    page.getSort(),
                    page.getTotalPages(),
                    page.getTotalElements()
            );
        }
    }
}
