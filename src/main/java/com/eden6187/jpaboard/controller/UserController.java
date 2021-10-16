package com.eden6187.jpaboard.controller;

import com.eden6187.jpaboard.common.ApiResponse;
import com.eden6187.jpaboard.common.ErrorResponse;
import com.eden6187.jpaboard.exception.DuplicatedUserNameException;
import com.eden6187.jpaboard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // start -- [POST /api/v1/users]
  @PostMapping
  public ResponseEntity<ApiResponse<AddUserResponseDto>> addUser(
      @RequestBody AddUserRequestDto saveUserDto) {
    Long createdUserId = userService.addUser(saveUserDto);
    return ResponseEntity.ok(
        ApiResponse.ok(
            AddUserResponseDto
                .builder()
                .userId(createdUserId)
                .build()
        )
    );
  }

  @ExceptionHandler(DuplicatedUserNameException.class)
  public ResponseEntity<ErrorResponse> handleDuplicatedUserNameException(
      DuplicatedUserNameException ex) {
    return ResponseEntity
        .badRequest()
        .body(
            ErrorResponse.of(ex.getErrorCode())
        );
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class AddUserRequestDto {

    final String name;
    final Integer age;
    final String hobby;
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class AddUserResponseDto {

    final Long userId;
  }
}
