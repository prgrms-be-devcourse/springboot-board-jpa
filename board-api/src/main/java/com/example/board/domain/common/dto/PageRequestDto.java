package com.example.board.domain.common.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public abstract class PageRequestDto {

    @NotNull(message = "페이지 번호를 입력해주세요.")
    @Min(value = 1, message = "1 이상의 올바른 페이지 번호를 입력해주세요.")
    protected Integer page = 1;

    @NotNull(message = "페이지 당 글 개수를 입력해주세요.")
    @Min(value = 1, message = "1 이상의 올바른 페이지 당 글 개수를 입력해주세요.")
    @Max(value = 100, message = "100 이하의 올바른 페이지 당 글 개수를 입력해주세요.")
    protected Integer size = 10;

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
