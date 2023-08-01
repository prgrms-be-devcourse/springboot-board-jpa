package com.programmers.springbootboardjpa.domain.post.controller;

import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.post.service.PostService;
import com.programmers.springbootboardjpa.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponseDto> create(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto) {
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);

        return ApiResponse.ok(postResponseDto);
    }

    @GetMapping
    public ApiResponse<Page<PostResponseDto>> findAll(Pageable pageable) {
        Page<PostResponseDto> postResponseDtos = postService.findAll(pageable);

        return ApiResponse.ok(postResponseDtos);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.findById(id);

        return ApiResponse.ok(postResponseDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<PostResponseDto> update(@PathVariable Long id, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto) {
        PostResponseDto postResponseDto = postService.update(id, postUpdateRequestDto);

        return ApiResponse.ok(postResponseDto);
    }
}
