package com.eden6187.jpaboard.service.converter;

import com.eden6187.jpaboard.controller.UserController.AddUserRequestDto;
import com.eden6187.jpaboard.model.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

  public User convertUser(AddUserRequestDto saveUserRequestDto) {
    return User.builder()
        .name(saveUserRequestDto.getName())
        .age(saveUserRequestDto.getAge())
        .hobby(saveUserRequestDto.getHobby())
        .createdAt(LocalDateTime.now())
        .build();
  }

}
