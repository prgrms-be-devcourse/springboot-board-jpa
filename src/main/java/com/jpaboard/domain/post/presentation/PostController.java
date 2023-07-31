package com.jpaboard.domain.post.presentation;

import com.jpaboard.domain.post.application.PostService;
import com.jpaboard.domain.post.dto.PostCreateRequest;
import com.jpaboard.domain.post.dto.PostResponse;
import com.jpaboard.domain.post.dto.PostUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<Long> postCreate(@RequestBody @Valid PostCreateRequest request) {
        Long postId = postService.createPost(request);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> postDetail(@PathVariable Long id) {
        PostResponse response = postService.findPostById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> findByCondition(
            @RequestParam(name = "title", required = false) Optional<String> title,
            @RequestParam(name = "content", required = false) Optional<String> content,
            @RequestParam(name = "keyword", required = false) Optional<String> keyword,
            @PageableDefault Pageable pageable
    ){
        if (title.isPresent()) {
            Page<PostResponse> response = postService.findPostByTitle(title.get(), pageable);
            return ResponseEntity.ok(response);
        }
        if (content.isPresent()) {
            Page<PostResponse> response = postService.findPostByContent(content.get(), pageable);
            return ResponseEntity.ok(response);
        }
        if (keyword.isPresent()) {
            Page<PostResponse> response = postService.findByKeyword(keyword.get(), pageable);
            return ResponseEntity.ok(response);
        }

        Page<PostResponse> response = postService.findPosts(pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> postUpdate(@PathVariable Long id, @RequestBody @Valid PostUpdateRequest request) {
        postService.updatePost(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> postDelete(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

}
