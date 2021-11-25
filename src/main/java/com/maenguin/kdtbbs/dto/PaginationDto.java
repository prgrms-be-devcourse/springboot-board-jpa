package com.maenguin.kdtbbs.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PaginationDto {

    private Integer totalPages;

    private Long totalElements;

    private Integer currentPage;

    private Long offset;

    private Integer size;

    public PaginationDto(Page page) {
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getPageable().getPageNumber();
        this.offset = page.getPageable().getOffset();
        this.size = page.getSize();
    }
}
