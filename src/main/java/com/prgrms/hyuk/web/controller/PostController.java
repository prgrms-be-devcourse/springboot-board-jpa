package com.prgrms.hyuk.web.controller;

import com.prgrms.hyuk.dto.ApiResponse;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.service.PostService;
import java.net.URI;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<URI> save(@RequestBody PostCreateRequest postCreateRequest) {
        return ApiResponse.created(URI.create("/posts/" + postService.create(postCreateRequest)));
    }

    @GetMapping
    public ApiResponse<List<PostDto>> getAll(
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ApiResponse.ok(postService.findPosts(offset, limit));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) {
        return ApiResponse.ok(postService.findPost(id));
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updatePost(
        @PathVariable Long id,
        @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ApiResponse.ok(postService.updatePost(id, postUpdateRequest));
    }
}
