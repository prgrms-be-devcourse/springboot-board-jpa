package com.prgrms.boardjpa.posts.controller;

import com.prgrms.boardjpa.common.CommonResponse;
import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  @Transactional(readOnly = true)
  public CommonResponse<Page<PostDto>> getPosts(Pageable pageable) {
    var postDtos = postService.getPosts(pageable);
    return CommonResponse.ok(postDtos);
  }

  @GetMapping("/{id}")
  @Transactional(readOnly = true)
  public CommonResponse<PostDto> getPost(@PathVariable Long id) {
    var postDto = postService.getPost(id);
    return CommonResponse.ok(postDto);
  }

  @PostMapping
  public CommonResponse<PostDto> createPost(
      @RequestBody @Valid PostRequest postRequest
  ) {
    var postDto = postService.createPost(postRequest);
    return CommonResponse.ok(postDto);
  }

  @PostMapping("/{id}")
  @Transactional
  public CommonResponse<PostDto> updatePost(
      @PathVariable Long id,
      @RequestBody @Valid PostRequest postRequest
  ) {
    var postDto = postService.updatePost(id, postRequest);
    return CommonResponse.ok(postDto);
  }
}
