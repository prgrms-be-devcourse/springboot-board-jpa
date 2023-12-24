package com.example.board.domain.post.controller;

import com.example.board.domain.common.dto.PageResponseDto;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.service.PostService;
import com.example.board.global.security.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
        Long currentUserId = SecurityUtil.getCurrentUserIdCheck();
        PostResponse post = postService.createPost(currentUserId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.id())
                .toUri();
        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.findPostByIdAndUpdateView(id);
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
            @Valid @RequestBody PostUpdateRequest request) {

        PostResponse post = postService.updatePost(id, request);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/list")
    public ResponseEntity<Void> deletePostsByIds(@RequestParam List<Long> ids) {
        postService.deletePostsByIds(ids);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.noContent()
                .build();
    }
}
