package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.PostResponse;
import com.prgrms.jpa.controller.dto.post.PostsResponse;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import com.prgrms.jpa.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CreatePostRequest createPostRequest) {
        long id = postService.create(createPostRequest);
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
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody UpdatePostRequest updatePostRequest) {
        postService.update(id, updatePostRequest);
        return ResponseEntity.ok().build();
    }
}
