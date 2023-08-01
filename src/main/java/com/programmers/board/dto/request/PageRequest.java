package com.programmers.board.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public abstract class PageRequest {
    @PositiveOrZero(message = "페이지 번호는 0 이상이어야 합니다.")
    private final int page;

    @Positive(message = "페이지 사이즈는 0보다 커야합니다.")
    private final int size;

    protected PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
