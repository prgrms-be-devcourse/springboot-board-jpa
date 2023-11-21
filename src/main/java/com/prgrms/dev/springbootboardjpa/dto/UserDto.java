package com.prgrms.dev.springbootboardjpa.dto;

import com.prgrms.dev.springbootboardjpa.domain.user.Hobby;
import lombok.Builder;

@Builder
public record UserDto(Long id, String name, Hobby hobby, int age) {
}
