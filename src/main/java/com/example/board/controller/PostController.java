package com.example.board.controller;

import com.example.board.ApiResponse;
import com.example.board.dto.PostDto;
import com.example.board.exception.PostNotFoundException;
import com.example.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ApiResponse<String> postNotFoundHandler(PostNotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> requestPageOfPosts(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> requestOnePost(@PathVariable Long id) {
        PostDto foundPostDto = postService.findById(id);
        return ApiResponse.ok(foundPostDto);
    }

    @PostMapping
    public ApiResponse<Long> uploadPost(@RequestBody PostDto postDto) {
        Long savedPostId = postService.save(postDto);
        return ApiResponse.ok(savedPostId);
    }

    @PostMapping("/{id}")
    public ApiResponse<Long> editPost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postDto.setId(id);
        Long postId = postService.editPost(postDto);
        return ApiResponse.ok(postId);
    }

}
