package com.devcourse.springjpaboard.application.post.controller;

import static org.springframework.http.HttpStatus.OK;

import com.devcourse.springjpaboard.application.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.application.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.application.post.service.PostServiceImpl;
import com.devcourse.springjpaboard.application.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.core.exception.NotFoundException;
import com.devcourse.springjpaboard.core.util.ApiResponse;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

  private final PostServiceImpl postService;

  public PostController(PostServiceImpl postService) {
    this.postService = postService;
  }

  @GetMapping("")
  public ApiResponse<Page<PostResponse>> getAllPosts(Pageable pageable) {
    Page<PostResponse> posts = postService.findAll(pageable);
    return ApiResponse.ok(OK, posts);
  }

  @GetMapping("/{id}")
  public ApiResponse<PostResponse> getPostById(@PathVariable Long id) throws NotFoundException {
    PostResponse post = postService.findOne(id);
    return ApiResponse.ok(OK, post);
  }

  @PostMapping("")
  public ApiResponse<PostResponse> writePost(
      @Valid @RequestBody CreatePostRequest createPostRequest) {
    PostResponse post = postService.save(createPostRequest);
    return ApiResponse.ok(OK, post);
  }

  @PatchMapping("/{id}")
  public ApiResponse<PostResponse> updatePost(@PathVariable Long id,
      @Valid @RequestBody UpdatePostRequest updatePostRequest)
      throws NotFoundException {
    PostResponse updatePost = postService.update(id, updatePostRequest);
    return ApiResponse.ok(OK, updatePost);
  }
}
