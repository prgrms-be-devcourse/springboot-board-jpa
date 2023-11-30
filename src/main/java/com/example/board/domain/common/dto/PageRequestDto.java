package com.example.board.domain.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
public abstract class PageRequestDto {

    @NotNull(message = "페이지 번호를 입력해주세요.")
    @Min(value = 1, message = "1 이상의 올바른 페이지 번호를 입력해주세요.")
    protected Integer page;

    @NotNull(message = "페이지 당 글 개수를 입력해주세요.")
    @Min(value = 1, message = "1 이상의 올바른 페이지 당 글 개수를 입력해주세요.")
    protected Integer size;

    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    public PageRequestDto(Integer page, Integer size) {
        this.page = (page != null && page > 0) ? page : 1;
        this.size = (size != null && size > 0) ? size : 10;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
