package com.springboard.post.controller;

import com.springboard.post.dto.CreatePostRequest;
import com.springboard.post.dto.CreatePostResponse;
import com.springboard.post.dto.FindPostResponse;
import com.springboard.post.dto.UpdatePostRequest;
import com.springboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class V1PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<Page<FindPostResponse>> getAll(Pageable pageable) {
        Page<FindPostResponse> responses = postService.findAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> register(@RequestBody CreatePostRequest request) {
        CreatePostResponse response = postService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindPostResponse> getOne(@PathVariable Long id) {
        FindPostResponse response = postService.findOne(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FindPostResponse> modify(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
        FindPostResponse response = postService.updateOne(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        postService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
