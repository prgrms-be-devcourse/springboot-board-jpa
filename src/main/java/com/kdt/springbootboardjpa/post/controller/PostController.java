package com.kdt.springbootboardjpa.post.controller;

import com.kdt.springbootboardjpa.post.service.PostService;
import com.kdt.springbootboardjpa.post.service.dto.CreatePostRequest;
import com.kdt.springbootboardjpa.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> save(@RequestBody CreatePostRequest postRequest) {
        PostResponse post = postService.save(postRequest);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        PostResponse one = postService.findById(id);
        return ResponseEntity.ok(one);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll(Pageable pageable) {
        Page<PostResponse> all = postService.findAll(pageable);
        return ResponseEntity.ok(all.getContent());
    }
}