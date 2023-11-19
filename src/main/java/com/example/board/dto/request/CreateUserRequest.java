package com.example.board.dto.request;

import lombok.Builder;

@Builder
public record CreateUserRequest(String name, Integer age, String hobby) {
}
