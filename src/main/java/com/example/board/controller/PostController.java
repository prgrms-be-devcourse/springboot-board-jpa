package com.example.board.controller;

import com.example.board.dto.PostDto;
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
    public ResponseEntity<PostDto> getOnePost(@PathVariable Long id) throws NotFoundException {
        PostDto post = postService.getOnePost(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> getAllPost(Pageable pageable) {
        Page<PostDto> posts = postService.getAllPostByPage(pageable);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto) {
        PostDto saved = postService.writePost(postDto);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) throws NotFoundException {
        PostDto updated = postService.updatePost(id, postDto);
        return ResponseEntity.ok(updated);
    }
}
