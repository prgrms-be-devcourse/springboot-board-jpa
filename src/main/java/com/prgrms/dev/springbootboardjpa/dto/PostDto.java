package com.prgrms.dev.springbootboardjpa.dto;

import lombok.Builder;

@Builder
public record PostDto(Long id, String title, String content, UserDto userDto) {
}
