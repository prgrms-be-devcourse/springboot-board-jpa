package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.dto.*;
import com.maenguin.kdtbbs.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;
    private final OptimisticLockTryer optimisticLockTryer;

    public PostRestController(PostService postService, OptimisticLockTryer optimisticLockTryer) {
        this.postService = postService;
        this.optimisticLockTryer = optimisticLockTryer;
    }

    @GetMapping
    public ApiResponse<PostListDto> searchAllPosts(
            @PageableDefault(size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(postService.getAllPosts(pageable));
    }

    @GetMapping(path = "{id}")
    public ApiResponse<PostDto> searchPostById(@PathVariable("id") Long postId) {
        PostDto postDto = optimisticLockTryer.attempt(() -> postService.getPostById(postId), 10);
        return ApiResponse.success(postDto);
    }

    @PostMapping
    public ApiResponse<PostAddResDto> registerPost(@RequestBody PostAddDto postAddDto) {
        return ApiResponse.success(postService.savePost(postAddDto));
    }

    @PostMapping(path = "{id}")
    public ApiResponse<PostAddResDto> editPost(@PathVariable("id") Long postId, @RequestBody PostAddDto postAddDto) {
        return ApiResponse.success(postService.editPost(postId, postAddDto));
    }
}
