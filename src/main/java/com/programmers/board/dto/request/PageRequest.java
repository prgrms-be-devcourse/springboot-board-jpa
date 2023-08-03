package com.programmers.board.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PageRequest {
    private static final String PAGE_VALIDATE = "페이지 번호는 0 이상이어야 합니다";
    private static final String SIZE_VALIDATE = "페이지 사이즈는 0 보다 커야합니다";

    @PositiveOrZero(message = PAGE_VALIDATE)
    private final int page;

    @Positive(message = SIZE_VALIDATE)
    private final int size;
}
