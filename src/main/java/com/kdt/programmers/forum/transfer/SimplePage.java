package com.kdt.programmers.forum.transfer;

import lombok.Getter;

import java.util.List;

@Getter
public class SimplePage {
    private final List<PostDto> content;
    private final long totalPages;
    private final long totalElements;

    public SimplePage(List<PostDto> content, long totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
