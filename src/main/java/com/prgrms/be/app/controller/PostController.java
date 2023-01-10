package com.prgrms.be.app.controller;

import com.prgrms.be.app.domain.dto.*;
import com.prgrms.be.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<Long> save(@RequestBody @Valid PostCreateRequest postCreateRequest) {
        Long postId = postService.createPost(postCreateRequest);
        return ApiResponse.created(
                postId,
                ResponseMessage.CREATED);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> getOne(@PathVariable Long id) {
        PostDetailResponse postDetailResponse = postService.findById(id);
        return ApiResponse.ok(
                postDetailResponse,
                ResponseMessage.FINDED_ONE);
    }

    @GetMapping
    public ApiResponse<PostsResponse> getAll(@RequestBody PostPageRequest postPageRequest) {
        PostsResponse postPages = postService.findAll(postPageRequest.of());
        return ApiResponse.ok(
                postPages,
                ResponseMessage.FINDED_ALL
        );
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> update(
            @PathVariable Long id,
            @RequestBody @Valid PostUpdateRequest request) {
        Long postId = postService.updatePost(id, request);
        return ApiResponse.ok(
                postId,
                ResponseMessage.UPDATED);
    }
}
