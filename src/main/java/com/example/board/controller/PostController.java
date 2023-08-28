package com.example.board.controller;


import com.example.board.domain.service.PostService;
import com.example.board.dto.post.PostRequestDto;
import com.example.board.dto.post.PostResponseDto;
import com.example.board.dto.post.PostWithCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity<Long> creatPost(@Valid @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(requestDto));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findPostsPaged(Pageable pageable) {
        return ResponseEntity.ok(postService.findPostsPaged(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPost(postId));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<PostWithCommentResponseDto> getPostWithComment(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPostWithComment(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequestDto requestDto) {
        postService.updatePost(postId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
