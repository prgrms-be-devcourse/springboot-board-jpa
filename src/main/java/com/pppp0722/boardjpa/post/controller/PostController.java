package com.pppp0722.boardjpa.post.controller;

import com.pppp0722.boardjpa.post.dto.PostDto;
import com.pppp0722.boardjpa.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> save(@RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        PostDto postDto = postService.findById(id);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> allPostDtos = postService.findAll(pageable);
        return ResponseEntity.ok(allPostDtos);
    }
}
