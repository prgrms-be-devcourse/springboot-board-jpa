package com.prgrms.dev.springbootboardjpa.dto;

import lombok.Builder;

@Builder
public record PostUpdateRequestDto(String title, String content) {

    public PostUpdateRequestDto {
        validateNull(title);
        validateNull(content);
    }

    private void validateNull(String input) {
        if(input.isEmpty() || input.isBlank()) throw new NullPointerException("NPE");
    }
}
