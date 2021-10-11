package com.prgrms.dlfdyd96.board.post.controller;

import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.service.PostService;
import com.prgrms.dlfdyd96.board.util.api.ApiResponse;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // <- 이렇게하면 HTTP Status code : 500 에러 .
  private ApiResponse<String> exceptionHandler(Exception exception) {
    return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND) // <- 이렇게하면 HTTP Status code : 500 에러 .
  private ApiResponse<String> notFoundHandler(NotFoundException exception) {
    return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  @GetMapping("/posts")
  public ApiResponse<Page<PostDto>> getAll(
      Pageable pageable
  ) {
    return ApiResponse.ok(postService.findPosts(pageable));
  }

  @GetMapping("/posts/{id}")
  public ApiResponse<PostDto> getOne(
      @PathVariable Long id
  ) throws NotFoundException {
    return ApiResponse.ok(postService.findOne(id));
  }

  @PostMapping("/posts")
  public ApiResponse<Long> save(@RequestBody PostDto postDto) {
    return ApiResponse.ok(postService.save(postDto));
  }
}
