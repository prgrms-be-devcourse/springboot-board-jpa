package com.programmers.boardjpa.post.dto;

import lombok.Builder;

import java.util.List;

public record PostPageResponseDto (List<PostResponseDto> data, int page, int size) {

    @Builder
    public PostPageResponseDto(List<PostResponseDto> data, int page, int size) {
        this.data = data;
        this.page = page;
        this.size = size;
    }
}
