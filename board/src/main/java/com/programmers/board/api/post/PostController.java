package com.programmers.board.api.post;

import com.programmers.board.api.common.ApiResponse;
import com.programmers.board.core.post.application.PostService;
import com.programmers.board.core.post.application.dto.PostCreateRequest;
import com.programmers.board.core.post.application.dto.PostResponse;
import com.programmers.board.core.post.application.dto.PostUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
@RequestMapping("/api/v1/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public ApiResponse<Page<PostResponse>> getAll(Pageable pageable) {
    return ApiResponse.ok(postService.findPosts(pageable));
  }

  @PostMapping
  public ApiResponse<PostResponse> save(@RequestBody PostCreateRequest postCreateRequest) {
    return ApiResponse.ok(postService.save(postCreateRequest));
  }

  @GetMapping("/{id}")
  public ApiResponse<PostResponse> getOne(@PathVariable Long id) {
    return ApiResponse.ok(postService.findOne(id));
  }

  @PutMapping("/{id}")
  public ApiResponse<PostResponse> update(
      @PathVariable Long id,
      @RequestBody PostUpdateRequest postUpdateRequest
  ) {
    return ApiResponse.ok(postService.update(id, postUpdateRequest));
  }

}
