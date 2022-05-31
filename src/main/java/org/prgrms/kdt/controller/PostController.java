package org.prgrms.kdt.controller;

import static java.text.MessageFormat.format;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.common.IdResponse;
import org.prgrms.kdt.dto.PostDto.PostRequest;
import org.prgrms.kdt.dto.PostDto.PostResponse;
import org.prgrms.kdt.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<Page<PostResponse>> findPosts(
      final Pageable pageable
  ) {
    Page<PostResponse> postResponses = postService.findPosts(pageable);
    return ResponseEntity.ok(postResponses);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponse> findPost(
      @PathVariable Long postId
  ) {
    PostResponse postResponse = postService.findPost(postId);
    return ResponseEntity.ok(postResponse);
  }

  @PostMapping
  public ResponseEntity<IdResponse<Long>> createPost(
      @RequestHeader(AUTHORIZATION) Long userId,
      @Valid @RequestBody PostRequest request
  ) {
    Long postId = postService.createPost(userId, request);
    IdResponse<Long> idResponse = new IdResponse<>(postId);
    URI postUri = URI.create(format("/posts/{0}", postId));
    return ResponseEntity.created(postUri).body(idResponse);
  }

  @PatchMapping("/{postId}")
  public ResponseEntity<IdResponse<Long>> updatePost(
      @PathVariable Long postId,
      @Valid @RequestBody PostRequest request
  ) {
    Long updatedId = postService.updatePost(postId, request);
    IdResponse<Long> idResponse = new IdResponse<>(updatedId);
    return ResponseEntity.ok(idResponse);
  }
}