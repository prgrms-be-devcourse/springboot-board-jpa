package com.prgrms.board.controller;

import com.prgrms.board.dto.*;
import com.prgrms.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final PostService postService;

    @PostMapping
    public ApiResponse<Long> register(@RequestBody @Valid PostCreateDto createDto) {
        Long savedPostId = postService.register(createDto);

        return ApiResponse.ok(savedPostId);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto responseDto = postService.findById(id);

        return ApiResponse.ok(responseDto);
    }

    @GetMapping
    public ApiResponse<CursorResult> findAll(Long cursorId, Integer size) {
        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        CursorResult cursorResult = postService.findAll(cursorId, PageRequest.of(0, size));
        return ApiResponse.ok(cursorResult);
    }

    @PutMapping
    public ApiResponse<Long> update(@RequestBody @Valid PostUpdateDto postUpdateDto) {
        Long updatedPostId = postService.update(postUpdateDto);

        return ApiResponse.ok(updatedPostId);
    }
}
