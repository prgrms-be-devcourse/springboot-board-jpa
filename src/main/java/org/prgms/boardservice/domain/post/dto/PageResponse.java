package org.prgms.boardservice.domain.post.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<E>(
        List<E> data,
        PageableResponse<E> pageable
) {
    public PageResponse(Page<E> page) {
        this(page.getContent(), new PageableResponse<>(page));
    }

    private record PageableResponse<E>(
            boolean first,
            boolean last,
            int number,
            int size,
            String sort,
            int totalPages,
            long totalElements
    ) {
        private PageableResponse(Page<E> page) {
            this(
                    page.isFirst(),
                    page.isLast(),
                    page.getNumber(),
                    page.getSize(),
                    page.getSort().toString(),
                    page.getTotalPages(),
                    page.getTotalElements()
            );
        }
    }
}
