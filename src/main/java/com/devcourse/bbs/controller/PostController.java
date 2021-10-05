package com.devcourse.bbs.controller;

import com.devcourse.bbs.controller.bind.ApiResponse;
import com.devcourse.bbs.controller.bind.PostCreateRequest;
import com.devcourse.bbs.controller.bind.PostUpdateRequest;
import com.devcourse.bbs.domain.post.Post;
import com.devcourse.bbs.domain.post.PostDTO;
import com.devcourse.bbs.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostDTO>>> getPostsByPage
            (@RequestParam(name = "page") int page,
             @RequestParam(name = "size") int size) {
        return ResponseEntity.ok(ApiResponse.success(postService.findPostsByPage(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPost(@PathVariable(name = "id") long id) {
        PostDTO postDTO = postService.findPostById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Post with given id not found.");
        });
        return ResponseEntity.ok(ApiResponse.success(postDTO));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@RequestBody PostCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(postService.createPost(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePost(@RequestBody PostUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(postService.updatePost(request)));
    }
}
