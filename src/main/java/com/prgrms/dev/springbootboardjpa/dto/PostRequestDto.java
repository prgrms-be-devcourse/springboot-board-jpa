package com.prgrms.dev.springbootboardjpa.dto;

import lombok.Builder;

@Builder
public record PostRequestDto(String title, String content) {
}
