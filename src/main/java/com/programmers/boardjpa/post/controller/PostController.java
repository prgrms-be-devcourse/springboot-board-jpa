package com.programmers.boardjpa.post.controller;

import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.dto.PostUpdateRequestDto;
import com.programmers.boardjpa.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable(name = "id") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> insertPost(@RequestBody PostInsertRequestDto postInsertRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.insertPost(postInsertRequestDto));
    }

    @PatchMapping
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return ResponseEntity.ok(postService.updatePost(postUpdateRequestDto));
    }
}
