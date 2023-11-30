package com.example.board.domain.post.dto;

import com.example.board.domain.common.dto.PageRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostPageCondition extends PageRequestDto {
    private final String email;
    private final String title;

    @Builder
    public PostPageCondition(Integer page, Integer size, String email, String title) {
        super(page, size);
        this.email = email;
        this.title = title;
    }
}
