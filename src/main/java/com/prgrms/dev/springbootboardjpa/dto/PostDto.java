package com.prgrms.dev.springbootboardjpa.dto;

import com.prgrms.dev.springbootboardjpa.domain.user.User;
import lombok.Builder;

@Builder
public record PostDto(Long id, String title, String content, User user) {
}
