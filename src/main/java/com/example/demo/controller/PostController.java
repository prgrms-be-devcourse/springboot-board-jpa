package com.example.demo.controller;

import com.example.demo.ApiResponse;
import com.example.demo.dto.PostDto;
import com.example.demo.service.PostService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        PostDto returnPost = postService.findOne(id);
        return ApiResponse.ok(returnPost);
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long postId = postService.save(postDto);
        return ApiResponse.ok(postId);
    }
}
