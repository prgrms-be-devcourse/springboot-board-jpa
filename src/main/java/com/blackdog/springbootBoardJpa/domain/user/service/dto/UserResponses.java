package com.blackdog.springbootBoardJpa.domain.user.service.dto;

import org.springframework.data.domain.Page;

public record UserResponses(
        Page<UserResponse> userResponses
) {
}
