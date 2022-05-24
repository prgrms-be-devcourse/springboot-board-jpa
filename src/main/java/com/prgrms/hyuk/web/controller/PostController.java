package com.prgrms.hyuk.web.controller;

import com.prgrms.hyuk.dto.ApiResponse;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.service.PostService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostCreateRequest postCreateRequest) {
        return ApiResponse.ok(postService.create(postCreateRequest));
    }

    @GetMapping("/posts")
    public ApiResponse<List<PostDto>> getAll(
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ApiResponse.ok(postService.findPosts(offset, limit));
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) {
        return ApiResponse.ok(postService.findPost(id));
    }

    @PatchMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(
        @PathVariable Long id,
        @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ApiResponse.ok(postService.updatePost(id, postUpdateRequest));
    }
}
