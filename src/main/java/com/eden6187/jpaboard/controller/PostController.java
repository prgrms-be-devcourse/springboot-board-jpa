package com.eden6187.jpaboard.controller;

import com.eden6187.jpaboard.common.ApiResponse;
import com.eden6187.jpaboard.common.ErrorResponse;
import com.eden6187.jpaboard.exception.not_found.NotFoundException;
import com.eden6187.jpaboard.exception.not_found.UserNotFoundException;
import com.eden6187.jpaboard.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
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

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(
      UserNotFoundException notFoundException) {
    return ResponseEntity
        .badRequest()
        .body(
            ErrorResponse.of(notFoundException.getErrorCode())
        );
  }

  @PostMapping("/{id}")
  ResponseEntity<ApiResponse<UpdatePostResponseDto>> updatePost(
      @PathVariable("id") Long postId,
      @RequestBody() UpdatePostRequestDto requestDto
  ) {

    UpdatePostResponseDto dto = postService.updatePost(requestDto, postId);
    return ResponseEntity.ok(
        ApiResponse.ok(dto)
    );
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class AddPostRequestDto {

    final Long userId;
    final String title;
    final String content;
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class AddPostResponseDto {

    final Long postId;
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class UpdatePostRequestDto {

    final Long userId;
    final String title;
    final String content;
  }

  @AllArgsConstructor
  @Getter
  @Builder
  public static class UpdatePostResponseDto {

    final String title;
    final String content;
  }
}
