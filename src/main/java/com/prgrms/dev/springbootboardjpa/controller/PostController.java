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
@AllArgsConstructor // 생성자 lombok ...
@RequestMapping("/api/v1/posts") // 새로운 요구사항 -> native에서는 하위 버전을 고려하기 위해, 에러 발생 시 mapping되는 범위
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
    } // body에서 전부 받는 방식으로

    //수정
    // post, put, patch ... HTTP 메서드 - 멱등성
    @PostMapping("/{id}")
    public ApiResponse<PostDto> update(@RequestBody PostRequestDto requestDto, @PathVariable Long id) {
        PostDto update = postService.update(requestDto, id);
        return ApiResponse.ok(update);
    }

    // 서로 다른 리턴 값을 줄 때 컴파일 시점에서 잡아줄 수 있는 에러 ...
}
