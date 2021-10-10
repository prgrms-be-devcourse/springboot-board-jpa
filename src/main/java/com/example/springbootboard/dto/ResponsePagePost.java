package com.example.springbootboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@NoArgsConstructor
@Getter
public class ResponsePagePost {
    private int page;
    private int size;
    private boolean first;
    private boolean last;
    private int totalPages;
    private long totalElements;
    private List<ResponsePost> posts;

    @Builder
    public ResponsePagePost(int page, int size, boolean first, boolean last, int totalPages, long totalElements, List<ResponsePost> posts) {
        this.page = page;
        this.size = size;
        this.first = first;
        this.last = last;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.posts = posts;
    }
}
