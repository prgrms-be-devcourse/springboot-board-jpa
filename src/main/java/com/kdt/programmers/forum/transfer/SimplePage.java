package com.kdt.programmers.forum.transfer;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SimplePage<T> {
    private final List<T> content;
    private final long totalPages;
    private final long totalElements;

    public SimplePage(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}
