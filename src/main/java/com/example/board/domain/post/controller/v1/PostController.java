package com.example.board.domain.post.controller.v1;

import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.service.PostService;
import java.net.URI;
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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/posts/v1")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService){
    this.postService = postService;
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponse.Detail> getSinglePost(@PathVariable Long postId){
    PostResponse.Detail detail = postService.findById(postId);

    return ResponseEntity.ok(detail);
  }

  @GetMapping
  public ResponseEntity<Page<PostResponse.Shortcut>> getPage(Pageable pageable){
    Page<PostResponse.Shortcut> page = postService.findPage(pageable);

    return ResponseEntity.ok(page);
  }

  @PostMapping
  public ResponseEntity<Void> post(@RequestBody PostRequest postRequest){
    Long savedId = postService.save(postRequest);

    URI uri = UriComponentsBuilder.newInstance()
        .path("/posts/v1/{postId}")
        .build()
        .expand(savedId)
        .encode()
        .toUri();

    return ResponseEntity.created(uri)
        .build();
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest){
    Long updatedId = postService.update(postId, postRequest);

    URI uri = UriComponentsBuilder.newInstance()
        .path("/posts/v1/{postId}")
        .build()
        .expand(updatedId)
        .encode()
        .toUri();

    return ResponseEntity.created(uri)
        .build();
  }
}
