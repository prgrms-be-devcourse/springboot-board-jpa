package com.maenguin.kdtbbs.controller;

import com.maenguin.kdtbbs.dto.ApiResponse;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.dto.PostListDto;
import com.maenguin.kdtbbs.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ApiResponse<PostListDto> searchAllPosts(
            @PageableDefault(size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(postService.getAllPosts(pageable));
    }

    @GetMapping(path = "{id}")
    public ApiResponse<PostDto> searchPostById(@PathVariable("id") Long postId) {
        return ApiResponse.success(postService.getPostById(postId));
    }

    @PostMapping
    public ApiResponse<Long> registerPost(@RequestBody PostAddDto postAddDto) {
        Long id = postService.savePost(postAddDto);
        return ApiResponse.success(id);
    }

    @PostMapping(path = "{id}")
    public ApiResponse<Long> editPost(@PathVariable("id") Long postId, @RequestBody PostAddDto postAddDto) {
        Long id = postService.editPost(postId, postAddDto);
        return ApiResponse.success(id);
    }
}
