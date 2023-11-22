package com.prgrms.dev.springbootboardjpa.controller;

import com.prgrms.dev.springbootboardjpa.ApiResponse;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostRequestDto;
import com.prgrms.dev.springbootboardjpa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    //단건 조회 -> find
    @GetMapping("/{id}")
    public ApiResponse<PostDto> findById(@PathVariable Long id) {
        PostDto postDto = postService.findById(id);
        return ApiResponse.ok(postDto);
    }

    //페이지 조회 -> get
    @GetMapping
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> postDto = postService.getAll(pageable);
        return ApiResponse.ok(postDto);
    }

    //생성
    @PostMapping
    public ApiResponse<PostDto> create(@RequestBody PostRequestDto postRequestDto, @RequestParam Long userId) {
        PostDto postDto = postService.create(postRequestDto, userId);
        return ApiResponse.ok(postDto);
    }

    //수정
    @PostMapping("/{id}")
    public ApiResponse<PostDto> update(@RequestBody PostRequestDto requestDto, @PathVariable Long id) {
        PostDto update = postService.update(requestDto, id);
        return ApiResponse.ok(update);
    }
}
