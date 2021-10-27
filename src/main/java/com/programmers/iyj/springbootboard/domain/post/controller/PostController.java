package com.programmers.iyj.springbootboard.domain.post.controller;

import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.post.service.PostService;
import com.programmers.iyj.springbootboard.global.ApiResponse;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        PostDto one = postService.findOneById(id);
        return ApiResponse.ok(one);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @PostMapping("/posts")
    public ApiResponse<PostDto> save(@Valid @RequestBody PostDto postDto) {
        PostDto savedPostDto = postService.save(postDto);
        return ApiResponse.ok(savedPostDto);
    }

    @PatchMapping("/posts/{id}")
    public ApiResponse<PostDto> update(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        PostDto savedPostDto = postService.save(postDto, id);
        return ApiResponse.ok(savedPostDto);
    }
}
