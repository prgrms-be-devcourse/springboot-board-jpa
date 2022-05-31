package com.example.board.controller;

import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.service.PostService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getOnePost(@PathVariable Long id) throws NotFoundException {
        PostResponseDto post = postService.getOnePost(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getAllPost(Pageable pageable) {
        Page<PostResponseDto> posts = postService.getAllPostByPage(pageable);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto saved = postService.writePost(postRequestDto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) throws NotFoundException {
        PostResponseDto updated = postService.updatePost(id, postRequestDto);
        return ResponseEntity.ok(updated);
    }
}
