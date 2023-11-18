package org.prgms.springbootboardjpayu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.request.UpdatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request) {
        PostResponse response = postService.updatePost(id, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse response = postService.getPost(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
