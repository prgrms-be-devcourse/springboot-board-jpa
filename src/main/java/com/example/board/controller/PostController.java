package com.example.board.controller;

import com.example.board.ApiResponse;
import com.example.board.dto.PostDto;
import com.example.board.service.PostService;
import javassist.NotFoundException;
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
@RequestMapping("/api/v1/")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    // 게시글 조회
    // 1. 페이징 조회 : GET "/posts"
    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> requestPageOfPosts(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    // 2. 단건 조회 : GET "/posts/{id}"
    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> requestOnePost(@PathVariable Long id) throws NotFoundException {
        PostDto foundPostDto = postService.findById(id);
        return ApiResponse.ok(foundPostDto);
    }

    // 게시글 작성
    // POST "/posts"
    @PostMapping("/posts")
    public ApiResponse<Long> uploadPost(@RequestBody PostDto postDto) {
        Long savedPostId = postService.save(postDto);
        return ApiResponse.ok(savedPostId);
    }

    // 게시글 수정
    // POST "/posts/{id}" -> mockMVC를 이용한 테스트까지
    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> editPost(@PathVariable Long id, @RequestBody PostDto postDto) throws NotFoundException {
        PostDto editedPost = postService.editPost(id, postDto);
        return ApiResponse.ok(editedPost);
    }

}
