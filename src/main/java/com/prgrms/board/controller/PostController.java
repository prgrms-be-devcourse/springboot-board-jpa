package com.prgrms.board.controller;

import com.prgrms.board.dto.post.PostResponse;
import com.prgrms.board.dto.post.PostSaveRequest;
import com.prgrms.board.dto.post.PostUpdateRequest;
import com.prgrms.board.dto.post.SimplePostResponse;
import com.prgrms.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestBody @Valid PostSaveRequest saveRequest
    ) {
        PostResponse postResponse = postService.createPost(saveRequest);
        String resourceUrl = "/posts/" + postResponse.getPostId();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(resourceUrl));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(postResponse);
    }

    @GetMapping
    public ResponseEntity<List<SimplePostResponse>> getPagingPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Long userId
    ) {
        List<SimplePostResponse> pagingPosts = postService.findPostsByCriteria(page, userId);
        return ResponseEntity.ok(pagingPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(
            @PathVariable Long postId
    ) {
        PostResponse post = postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @RequestBody @Valid PostUpdateRequest updateRequest,
            @PathVariable Long postId
    ) {
        PostResponse postResponse = postService.updatePost(updateRequest, postId);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }
}
