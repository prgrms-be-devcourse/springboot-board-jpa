package org.prgrms.board.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageDto<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private long offset;
    private int pageSize;
    private int pageNumber;

    @Builder
    public PageDto(List<T> content, int totalPages, long totalElements, long offset, int pageSize, int pageNumber) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.offset = offset;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

}
