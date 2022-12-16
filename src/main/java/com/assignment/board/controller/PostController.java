package com.assignment.board.controller;

import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
import com.assignment.board.dto.post.PostUpdateDto;
import com.assignment.board.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalErrorHandler(Exception e)  {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping
    public ApiResponse<PostResponseDto> createPost(@RequestBody PostRequestDto postDto) throws NotFoundException {
        PostResponseDto savedPost = postService.createPost(postDto);
        return ApiResponse.ok(savedPost);
    }

    @GetMapping
    public ApiResponse<Page<PostResponseDto>> getAllPosts(Pageable pageable) {
        Page<PostResponseDto> allPost = postService.getAllPost(pageable);
        return ApiResponse.ok(allPost);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponseDto> getPostById(@PathVariable String postId) throws NotFoundException {
        PostResponseDto postDto = postService.getPostById(Long.parseLong(postId));
        return ApiResponse.ok(postDto);
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponseDto> updatePost(@PathVariable String postId, @RequestBody PostUpdateDto postUpdateDto) throws NotFoundException {
        postUpdateDto.setId(Long.parseLong(postId));
        PostResponseDto postDto = postService.updatePost(postUpdateDto);
        return ApiResponse.ok(postDto);
    }
}