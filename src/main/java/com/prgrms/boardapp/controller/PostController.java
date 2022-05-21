package com.prgrms.boardapp.controller;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.save(postDto));
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }
}
