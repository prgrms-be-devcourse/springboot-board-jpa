package com.prgrms.dev.springbootboardjpa.dto;

import lombok.Builder;

@Builder
public record PostCreateRequestDto(Long userId, String title, String content) {

    public PostCreateRequestDto {
        validateNull(title);
        validateNull(content);
    }

    private void validateNull(String input) {
        if(input.isEmpty() || input.isBlank()) throw new IllegalArgumentException("값을 입력 해야 합니다.");
    }
}
