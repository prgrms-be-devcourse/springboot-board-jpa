package com.example.board.domain.post.controller;

import com.example.board.domain.common.dto.PageResponseDto;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestParam String email, @Valid @RequestBody PostCreateRequest request) {
        PostResponse post = postService.createPost(email, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.id())
                .toUri();
        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.findPostById(id);
        postService.updatePostViewById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<PostResponse>> getPostsByCondition(
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size,
        @RequestParam(value = "email", defaultValue = "", required = false) String email,
        @RequestParam(value = "title", defaultValue = "", required = false) String title
    ) {
        PostPageCondition condition = new PostPageCondition(page, size, email, title);
        Page<PostResponse> posts = postService.findPostsByCondition(condition);
        return ResponseEntity.ok(PageResponseDto.of(posts));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @RequestParam String email,
            @Valid @RequestBody PostUpdateRequest request) {

        PostResponse post = postService.updatePost(id, email, request);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id, @RequestParam String email) {
        postService.deletePostById(id, email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/writer")
    public ResponseEntity<Void> deleteAllPostsByWriter(@RequestParam String email) {
        postService.deleteAllPostsByWriter(email);
        return ResponseEntity.noContent().build();
    }
}
