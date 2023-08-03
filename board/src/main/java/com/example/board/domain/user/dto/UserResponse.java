package com.example.board.domain.user.dto;

import com.example.board.domain.user.User;

public record UserResponse(Long id, String name, Integer age, String hobby, String createdAt,
                           String createdBy) {

  public static UserResponse from(User user) {
    return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(),
        user.getCreatedAt().toString(),
        user.getCreatedBy());
  }
}
