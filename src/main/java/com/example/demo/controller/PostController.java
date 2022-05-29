package com.example.demo.controller;

import com.example.demo.dto.PostDto;
import com.example.demo.exception.PostNotFoundException;
import com.example.demo.service.PostService;
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
    public ResponseEntity<PostDto> getOne(@PathVariable Long id) throws PostNotFoundException {
        PostDto returnPost = postService.findOne(id);
        return ResponseEntity.ok(returnPost);
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> save(@RequestBody PostDto postDto) {
        Long postId = postService.save(postDto);
        return ResponseEntity.ok(postId);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> getAllByPage(Pageable pageable) {
        Page<PostDto> allByPage = postService.findAllByPage(pageable);
        return ResponseEntity.ok(allByPage);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<PostDto> updateTitleAndContent(@PathVariable Long id, @RequestBody PostDto postDto) throws PostNotFoundException {
        PostDto updatedDto = postService.updateTitleAndContent(postDto, id);
        return ResponseEntity.ok(updatedDto);
    }
}
