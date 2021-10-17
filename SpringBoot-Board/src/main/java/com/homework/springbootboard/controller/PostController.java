package com.homework.springbootboard.controller;

import com.homework.springbootboard.dto.ApiResponse;
import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.exception.PostNotFoundException;
import com.homework.springbootboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getPost(@PathVariable Long id) {
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
