package com.assignment.bulletinboard.post.controller;

import com.assignment.bulletinboard.api.ApiResponse;
import com.assignment.bulletinboard.post.dto.PostSaveDto;
import com.assignment.bulletinboard.post.dto.PostUpdateDto;
import com.assignment.bulletinboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ApiResponse<Long> addPost(@RequestBody @Valid PostSaveDto postSaveDto) {
        Long postId = postService.save(postSaveDto);
        return ApiResponse.ok(postId);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return ApiResponse.ok(postService.update(postUpdateDto));
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostSaveDto> getPost(@PathVariable Long id) {
        return ApiResponse.ok(postService.findOne(id));
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostSaveDto>> getAllPost(Pageable pageable) {
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{id}")
    public ApiResponse<Long> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.ok(null);
    }
}
