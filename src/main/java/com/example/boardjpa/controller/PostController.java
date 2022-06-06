package com.example.boardjpa.controller;

import com.example.boardjpa.dto.*;
import com.example.boardjpa.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<PostsResponseDto> showPosts(
            @RequestParam(required = false, defaultValue = "0") Integer page
            , @RequestParam(required = false, defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getPosts(pageRequest));
    }

    @PostMapping()
    public ResponseEntity<CreatePostResponseDto> createPost(@RequestBody CreatePostRequestDto createPostRequestDto) {
        return ResponseEntity.ok(postService.createPost(createPostRequestDto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> showPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        postService.updatePost(postId, updatePostRequestDto);
        return ResponseEntity.ok().build();
    }
}
