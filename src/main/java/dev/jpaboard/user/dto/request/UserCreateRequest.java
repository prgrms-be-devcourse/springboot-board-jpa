package dev.jpaboard.user.dto.request;

import dev.jpaboard.user.domain.User;

public record UserCreateRequest(String email, String password, String name, int age, String hobby) {

  public static User toUser(UserCreateRequest request) {
    return User.builder()
            .email(request.email)
            .password(request.password)
            .name(request.name)
            .age(request.age)
            .hobby(request.hobby)
            .build();
  }

}
