package com.kdt.devboard.post.controller;

import com.kdt.devboard.Common.ApiResponse;
import com.kdt.devboard.post.Dto.PostInsertRequest;
import com.kdt.devboard.post.Dto.PostResponse;
import com.kdt.devboard.post.Dto.PostUpdateRequest;
import com.kdt.devboard.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Long> save(@RequestBody PostInsertRequest postRequest) throws NotFoundException {
        return ApiResponse.ok(postService.save(postRequest));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getOne(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(postService.findOne(id));
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> getAll(Pageable pageable) {
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @PutMapping
    public ApiResponse<PostResponse> updateOne(@RequestBody PostUpdateRequest postDto) throws NotFoundException {
        return ApiResponse.ok(postService.update(postDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> removeOne(@PathVariable Long id) throws NotFoundException {
        postService.deleteById(id);
        return ApiResponse.ok("delete complete");
    }

}
