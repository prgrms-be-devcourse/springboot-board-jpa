package com.programmers.springboard.controller;

import com.programmers.springboard.config.ApiResponse;
import com.programmers.springboard.dto.PostDto;
import com.programmers.springboard.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        PostDto one = postService.findOne(id);
        return ApiResponse.ok(one);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }


    @PatchMapping("/posts/{id}")
    public ApiResponse<Long> update(@PathVariable Long id, @RequestBody PostDto postDto) throws NotFoundException {
        Long postId = postService.update(postDto, id);
        return ApiResponse.ok(postId);
    }
}
