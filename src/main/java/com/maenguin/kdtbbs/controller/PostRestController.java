package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.dto.*;
import com.maenguin.kdtbbs.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static com.maenguin.kdtbbs.dto.ApiResponse.success;

@RestController
@RequestMapping("/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<PostListDto> searchAllPosts(
            @PageableDefault(size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(postService.getAllPosts(pageable));
    }

    @GetMapping(path = "{id}")
    public ApiResponse<PostDto> searchPostById(@PathVariable("id") Long postId) {
        return ApiResponse.success(postService.getPostById(postId));
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
