package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.dto.ApiResponse;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.dto.PostListDto;
import com.maenguin.kdtbbs.service.PostService;
import org.springframework.web.bind.annotation.*;

import static com.maenguin.kdtbbs.dto.ApiResponse.success;

@RestController
@RequestMapping("bbs/api/v1/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<PostListDto> searchAllPosts() {
        return ApiResponse.success(postService.getAllPosts());
    }

    @GetMapping(path = "{id}")
    public ApiResponse<PostDto> searchPostById(@PathVariable("id") Long postId) {
        return ApiResponse.success(postService.getPostById(postId));
    }

    @PostMapping
    public ApiResponse<Boolean> registerPost(@RequestBody PostAddDto postAddDto) {
        postService.savePost(postAddDto);
        return ApiResponse.success(true);
    }

    @PostMapping(path = "{id}")
    public ApiResponse<Boolean> editPost(@PathVariable("id") Long postId, @RequestBody PostAddDto postAddDto) {
        postService.editPost(postId, postAddDto);
        return ApiResponse.success(true);
    }
}
