package com.example.demo.controller;

import com.example.demo.ApiResponse;
import com.example.demo.dto.PostDto;
import com.example.demo.service.PostService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        PostDto returnPost = postService.findOne(id);
        return ApiResponse.ok(returnPost);
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long postId = postService.save(postDto);
        return ApiResponse.ok(postId);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAllByPage(Pageable pageable) {
        Page<PostDto> allByPage = postService.findAllByPage(pageable);
        return ApiResponse.ok(allByPage);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> updateTitleAndContent(@PathVariable Long id, @RequestBody PostDto postDto) throws NotFoundException {
        PostDto updatedDto = postService.updateTitleAndContent(postDto, id);
        return ApiResponse.ok(updatedDto);
    }
}
