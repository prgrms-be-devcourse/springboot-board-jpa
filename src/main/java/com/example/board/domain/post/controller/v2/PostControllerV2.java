package com.example.board.domain.post.controller.v2;

import com.example.board.domain.member.controller.v2.session.AuthenticatedMember;
import com.example.board.domain.member.controller.v2.session.SessionManager;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostResponse.Detail;
import com.example.board.domain.post.dto.PostResponse.Shortcut;
import com.example.board.domain.post.service.PostService;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/posts/v2")
public class PostControllerV2 {

  private final PostService postService;

  private final SessionManager sessionManager;

  public PostControllerV2(PostService postService, SessionManager sessionManager){
    this.postService = postService;
    this.sessionManager = sessionManager;
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Detail> getSinglePost(@PathVariable Long postId){
    PostResponse.Detail detail = postService.findById(postId);

    return ResponseEntity.ok(detail);
  }

  @GetMapping
  public ResponseEntity<Page<Shortcut>> getPage(Pageable pageable){
    Page<PostResponse.Shortcut> page = postService.findPage(pageable);

    return ResponseEntity.ok(page);
  }

  @PostMapping
  public ResponseEntity<Void> post(@RequestBody PostRequest postRequest, HttpServletRequest request){
    AuthenticatedMember member = sessionManager.getSession(request);
    Long savedId = postService.save(
        new PostRequest(
            postRequest.title(),
            postRequest.content(),
            member.getId())
    );

    URI uri = UriComponentsBuilder.newInstance()
        .path("/posts/v2/{postId}")
        .build()
        .expand(savedId)
        .encode()
        .toUri();

    return ResponseEntity.created(uri)
        .build();
  }

  @PutMapping("/{postId}")
  public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest,
      HttpServletRequest request){
    AuthenticatedMember member = sessionManager.getSession(request);
    Long updatedId = postService.update(postId,
        new PostRequest(
            postRequest.title(),
            postRequest.content(),
            member.getId())
    );

    URI uri = UriComponentsBuilder.newInstance()
        .path("/posts/v2/{postId}")
        .build()
        .expand(updatedId)
        .encode()
        .toUri();

    return ResponseEntity.created(uri)
        .build();
  }
}
