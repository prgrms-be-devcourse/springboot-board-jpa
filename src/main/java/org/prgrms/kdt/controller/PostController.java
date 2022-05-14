package org.prgrms.kdt.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.common.ApiResponse;
import org.prgrms.kdt.dto.PostDto.PostRequest;
import org.prgrms.kdt.dto.PostDto.PostResponse;
import org.prgrms.kdt.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

  private final PostService postService;

  @GetMapping
  public ApiResponse<Page<PostResponse>> findPosts(
      Pageable pageable
  ) {
    Page<PostResponse> postResponses = postService.findPosts(pageable);
    return ApiResponse.ok(postResponses);
  }

  @GetMapping("/{postId}")
  public ApiResponse<PostResponse> findPost(
      @PathVariable Long postId
  ) {
    PostResponse postResponse = postService.findPost(postId);
    return ApiResponse.ok(postResponse);
  }

  @PostMapping
  public ApiResponse<Long> createPost(
      @RequestHeader(AUTHORIZATION) Long userId,
      @Valid @RequestBody PostRequest request
  ) {
    Long postId = postService.createPost(userId, request);
    return ApiResponse.of(CREATED, postId);
  }

  @PatchMapping("/{postId}")
  public ApiResponse<Long> updatePost(
      @PathVariable Long postId,
      @Valid @RequestBody PostRequest request
  ) {
    Long updatedId = postService.updatePost(postId, request);
    return ApiResponse.ok(updatedId);
  }
}