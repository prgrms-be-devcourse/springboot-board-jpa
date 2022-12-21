package com.example.board.domain.post.controller;

import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.service.PostService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

  private final PostService postService;

  @GetMapping
  public ResponseEntity<Page<PostResponse.Shortcut>> getPage(Pageable pageable){
    Page<PostResponse.Shortcut> page = postService.findPage(pageable);

    return ResponseEntity.ok(page);
  }

  @PostMapping
  public ResponseEntity<Void> post(@RequestBody PostRequest postRequest){
    Long savedId = postService.save(postRequest);

    return ResponseEntity
        .created(
            URI.create("/post/" + savedId))
        .build();
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponse.Detail> getSinglePost(@PathVariable Long postId){
    PostResponse.Detail detail = postService.findById(postId);

    return ResponseEntity.ok(detail);
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest){
    Long updatedId = postService.update(postId, postRequest);

    return ResponseEntity
        .created(
            URI.create(String.format("/post/%d", updatedId)))
        .build();
  }
}
