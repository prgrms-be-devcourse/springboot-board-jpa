package com.programmers.board.controller;

import com.programmers.board.dto.PostRequestDto;
import com.programmers.board.dto.PostResponseDto;
import com.programmers.board.exception.NotFoundException;
import com.programmers.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
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

    @GetMapping("")
    public ApiResponse<Page<PostResponseDto>> getAll(Pageable pageable) {
        Page<PostResponseDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponseDto> getPost(@PathVariable int id) throws NotFoundException {
        PostResponseDto one = postService.find((long) id);
        return ApiResponse.ok(one);
    }

    @PostMapping("")
    public ApiResponse<Long> createPost(@RequestBody PostRequestDto postRequestDto) throws NotFoundException {
        Long id = postService.save(postRequestDto);
        return ApiResponse.ok(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable int id, @RequestBody PostRequestDto postRequestDto) throws NotFoundException {
        Long newId = postService.update((long)id, postRequestDto);
        return ApiResponse.ok(newId);
    }
}
