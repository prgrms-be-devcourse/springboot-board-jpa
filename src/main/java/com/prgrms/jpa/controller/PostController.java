package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.post.*;
import com.prgrms.jpa.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostIdResponse> create(@RequestBody @Valid CreatePostRequest createPostRequest) {
        PostIdResponse id = postService.create(createPostRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostsResponse> findAll(@PageableDefault(size = 15) Pageable pageable) {
        PostsResponse postsResponse = postService.findAll(pageable);
        return ResponseEntity.ok(postsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable long id) {
        PostResponse postResponse = postService.findById(id);
        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        postService.update(id, updatePostRequest);
        return ResponseEntity.ok().build();
    }
}
