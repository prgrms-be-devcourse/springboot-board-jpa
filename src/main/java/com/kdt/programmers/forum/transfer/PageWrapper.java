package com.kdt.programmers.forum.transfer;

import lombok.Getter;

import java.util.List;

@Getter
public class PageWrapper {
    private final List<PostWrapper> content;
    private final long totalPages;
    private final long totalElements;

    public PageWrapper(List<PostWrapper> content, long totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
