package com.example.boardjpa.controller;

import com.example.boardjpa.dto.CreatePostRequestDto;
import com.example.boardjpa.dto.PostResponseDto;
import com.example.boardjpa.dto.UpdatePostRequestDto;
import com.example.boardjpa.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<Page<PostResponseDto>> showPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @PostMapping("")
    public ResponseEntity<Long> createPost(@RequestBody CreatePostRequestDto createPostRequestDto) {
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
