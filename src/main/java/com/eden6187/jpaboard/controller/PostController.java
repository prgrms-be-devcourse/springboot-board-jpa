package com.eden6187.jpaboard.controller;

import com.eden6187.jpaboard.common.ApiResponse;
import com.eden6187.jpaboard.common.ErrorCode;
import com.eden6187.jpaboard.common.ErrorResponse;
import com.eden6187.jpaboard.exception.UserNotFoundException;
import com.eden6187.jpaboard.service.PostService;
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
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  // start -- [POST /api/v1/posts] - 게시물 추가
  @PostMapping
  ResponseEntity<ApiResponse<AddPostResponseDto>> addPost(
      @RequestBody AddPostRequestDto addPostRequestDto) {
    log.info("request add post");
    Long addedPostId = postService.addPost(addPostRequestDto);
    return ResponseEntity.ok(
        ApiResponse.ok(
            AddPostResponseDto
                .builder()
                .postId(addedPostId)
                .build()
        )
    );
  }

  // request DTO
  @AllArgsConstructor
  @Getter
  @Builder
  public static class AddPostRequestDto {

    final Long userId;
    final String title;
    final String content;
  }

  // response DTO
  @AllArgsConstructor
  @Getter
  @Builder
  public static class AddPostResponseDto {

    final Long postId;
  }

  // UserNotFoundException
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(
      UserNotFoundException userNotFoundException) {
    log.info("handle user not found exception");
    return ResponseEntity
        .badRequest()
        .body(
            ErrorResponse.of(ErrorCode.DUPLICATED_USER_NAME)
        );
  }
  // end -- [POST /api/v1/posts] - 게시물 추가
}
