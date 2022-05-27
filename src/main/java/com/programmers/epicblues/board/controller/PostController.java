package com.programmers.epicblues.board.controller;

import com.programmers.epicblues.board.dto.CreatePostRequest;
import com.programmers.epicblues.board.dto.PostResponse;
import com.programmers.epicblues.board.dto.UpdatePostRequest;
import com.programmers.epicblues.board.exception.InvalidRequestArgumentException;
import com.programmers.epicblues.board.exception.ResourceNotFoundException;
import com.programmers.epicblues.board.service.PostService;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {

  private final PostService postService;

  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getPostWithPage(Pageable pageable) {

    List<PostResponse> postResponseList = postService.getPosts(pageable);

    return ResponseEntity.ok(postResponseList);
  }

  @GetMapping("/posts/{postId}")
  public ResponseEntity<PostResponse> getPostById(@PathVariable long postId) {

    try {
      PostResponse postResponse = postService.getPostById(postId);
      return ResponseEntity.ok(postResponse);

    } catch (NoSuchElementException exception) {
      throw new ResourceNotFoundException("Invalid id", exception);
    }

  }

  @PostMapping("/posts")
  public ResponseEntity<PostResponse> createPost(
      @RequestBody @Valid CreatePostRequest postRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new InvalidRequestArgumentException(bindingResult);
    }

    PostResponse createdResult = postService.createPost(postRequest);

    return ResponseEntity.ok(createdResult);
  }

  @PutMapping("/posts")
  public ResponseEntity<PostResponse> updatePost(
      @RequestBody @Valid UpdatePostRequest updatePostRequest, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new InvalidRequestArgumentException(bindingResult);
    }

    PostResponse updatedResult = postService.updatePost(updatePostRequest);

    return ResponseEntity.ok(updatedResult);
  }

}
