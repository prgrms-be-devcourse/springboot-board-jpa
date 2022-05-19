package com.example.spring_jpa_post.post.controller;

import com.example.spring_jpa_post.post.dto.request.CreatePostRequest;
import com.example.spring_jpa_post.post.dto.response.FoundPostResponse;
import com.example.spring_jpa_post.post.dto.request.ModifyPostRequest;
import com.example.spring_jpa_post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<FoundPostResponse>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPost(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<FoundPostResponse> getOnePost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@Valid @RequestBody final CreatePostRequest createPostRequest) {
        return ResponseEntity.ok(postService.createPost(createPostRequest));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Long> modifyPost(@PathVariable Long postId,@Valid @RequestBody final ModifyPostRequest modifyPostRequest) {
        return ResponseEntity.ok(postService.modifyPost(postId, modifyPostRequest));
    }
}
