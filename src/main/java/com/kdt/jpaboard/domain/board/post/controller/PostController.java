package com.kdt.jpaboard.domain.board.post.controller;

import com.kdt.jpaboard.domain.board.ApiResponse;
import com.kdt.jpaboard.domain.board.post.dto.CreatePostDto;
import com.kdt.jpaboard.domain.board.post.dto.PostDto;
import com.kdt.jpaboard.domain.board.post.dto.UpdatePostDto;
import com.kdt.jpaboard.domain.board.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ApiResponse<String> internalServerException (HttpServerErrorException e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @GetMapping("")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> posts = postService.findAll(pageable);
        return ApiResponse.ok(posts);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDto> getOne(@PathVariable Long postId) throws NotFoundException {
        PostDto one = postService.findById(postId);
        return ApiResponse.ok(one);
    }

    @PostMapping("")
    public ApiResponse<Long> save(@RequestBody CreatePostDto postDto) {
        Long save = postService.save(postDto);
        return ApiResponse.ok(save);
    }

    @PutMapping("/{postId}")
    public ApiResponse<Long> update(@PathVariable Long postId,
                                    @RequestBody UpdatePostDto updatePostDto) throws NotFoundException {
        Long update = postService.update(postId, updatePostDto);
        return ApiResponse.ok(update);
    }

}
