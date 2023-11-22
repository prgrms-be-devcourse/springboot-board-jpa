package com.example.board.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
@Builder
public class PostPageCondition{
    @NotNull(message = "페이지 번호를 입력해주세요.")
    @PositiveOrZero(message = "1 이상의 올바른 페이지 번호를 입력해주세요.")
    Integer page;

    @NotNull(message = "페이지 당 글 개수를 입력해주세요.")
    @PositiveOrZero(message = "1 이상의 올바른 페이지 당 글 개수를 입력해주세요.")
    Integer size;

    String email;
    String title;

    public PostPageCondition() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
