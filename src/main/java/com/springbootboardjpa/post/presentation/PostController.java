package com.springbootboardjpa.post.presentation;

import com.springbootboardjpa.post.application.PostService;
import com.springbootboardjpa.post.dto.PostRequest;
import com.springbootboardjpa.post.dto.PostResponse;
import com.springbootboardjpa.post.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> save(@RequestBody PostRequest request) {
        PostResponse response = postService.save(request);
        return ResponseEntity.created(URI.create("/api/posts/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        PostResponse response = postService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PostsResponse> findAll() {
        PostsResponse response = postService.findAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable Long id, @RequestBody PostRequest request) {
        postService.updateById(id, request);
        return ResponseEntity.noContent().build();
    }
}
