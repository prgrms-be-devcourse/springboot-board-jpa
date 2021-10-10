package com.prgrms.dlfdyd96.board.post.controller;

import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.service.PostService;
import com.prgrms.dlfdyd96.board.util.api.ApiResponse;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @ExceptionHandler
  private ApiResponse<String> exceptionHandle(Exception exception) {
    return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  private ApiResponse<String> notFoundHandler(NotFoundException exception) {
    return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  @PostMapping("/posts")
  public ApiResponse<Long> save(@RequestBody PostDto postDto) {
    return ApiResponse.ok(postService.save(postDto));
  }



}
