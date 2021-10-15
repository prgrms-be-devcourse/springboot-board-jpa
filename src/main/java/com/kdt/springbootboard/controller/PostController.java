package com.kdt.springbootboard.controller;

import com.kdt.springbootboard.dto.PostDto;
import com.kdt.springbootboard.exception.PostNotFoundException;
import com.kdt.springbootboard.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getPost(@PathVariable Long id) throws PostNotFoundException {
        PostDto post = postService.findPost(id);
        return ApiResponse.ok(post);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getPosts(Pageable pageable) {
        return ApiResponse.ok(postService.findPosts(pageable));
    }

    @PostMapping("/posts")
    public ApiResponse<Long> createPost(@Valid @RequestBody PostDto postDto) {
        return ApiResponse.ok(postService.save(postDto));
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        return ApiResponse.ok(postService.update(id, postDto));
    }
}