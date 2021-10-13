package com.example.springbootboard.dto;

import com.example.springbootboard.dto.response.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PagePostDto {
    private int page;
    private int size;
    private boolean first;
    private boolean last;
    private int totalPages;
    private long totalElements;
    private List<PostDto> posts;

    @Builder
    public PagePostDto(int page, int size, boolean first, boolean last, int totalPages, long totalElements, List<PostDto> posts) {
        this.page = page;
        this.size = size;
        this.first = first;
        this.last = last;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.posts = posts;
    }
}
