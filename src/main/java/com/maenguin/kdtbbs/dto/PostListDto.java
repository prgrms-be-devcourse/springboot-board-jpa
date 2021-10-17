package com.maenguin.kdtbbs.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PostListDto {

    private List<PostDto> postList;
    private PaginationDto pagination;

    public PostListDto(Page<PostDto> page) {
        this.postList = page.getContent();
        this.pagination = new PaginationDto(page);
    }
}
