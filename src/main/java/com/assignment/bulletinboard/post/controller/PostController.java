package com.assignment.bulletinboard.post.controller;

import com.assignment.bulletinboard.ApiResponse;
import com.assignment.bulletinboard.post.dto.PostDto;
import com.assignment.bulletinboard.post.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/posts")
    public ApiResponse<Long> addPost(@RequestBody PostDto postDto) {
        Long postId = postService.save(postDto);
        return ApiResponse.ok(postId);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(
            @PathVariable Long id,
            @RequestBody PostDto postDto
    ) throws NotFoundException {
        return ApiResponse.ok(postService.update(postDto));
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getPost(
            @PathVariable Long id
    ) throws NotFoundException {
        return ApiResponse.ok(postService.findOne(id));
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAllPost(
            Pageable pageable
    ) {
        return ApiResponse.ok(postService.findAll(pageable));
    }
}
