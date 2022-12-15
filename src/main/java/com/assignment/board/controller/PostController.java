package com.assignment.board.controller;

import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
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
}