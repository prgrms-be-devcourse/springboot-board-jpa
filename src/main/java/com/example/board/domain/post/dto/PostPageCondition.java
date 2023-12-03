package com.example.board.domain.post.dto;

import com.example.board.domain.common.dto.PageRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostPageCondition extends PageRequestDto {
    String name;
    String title;

    public PostPageCondition(Integer page, Integer size, String name , String title) {
        this.page = page;
        this.size = size;
        this.name = name;
        this.title = title;
    }
}
