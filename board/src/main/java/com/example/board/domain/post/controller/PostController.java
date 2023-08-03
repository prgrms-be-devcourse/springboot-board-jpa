package com.example.board.domain.post.controller;

import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPost(@RequestBody @Valid PostCreateRequest postCreateRequest) {
    postService.createPost(postCreateRequest);
  }

  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponse getPost(@PathVariable("postId") Long postId) {
    return postService.getPost(postId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<PostResponse> getPosts(@Valid Pageable pageable) {
    return postService.getPosts(pageable);
  }

  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void updatePost(@PathVariable("postId") Long postId,
      @RequestBody @Valid PostUpdateRequest postUpdateRequest) {
    postService.updatePost(postId, postUpdateRequest);
  }
}