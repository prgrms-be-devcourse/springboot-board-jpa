package com.prgrms.dev.springbootboardjpa.dto;

import lombok.Builder;

@Builder
public record PostUpdateRequestDto(String title, String content) {
}
