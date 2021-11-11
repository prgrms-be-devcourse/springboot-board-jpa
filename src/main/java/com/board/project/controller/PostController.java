package com.board.project.controller;

import com.board.project.common.ApiResponse;
import com.board.project.dto.PostRequest;
import com.board.project.dto.PostResponse;
import com.board.project.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Long> save(@RequestBody PostRequest postRequest) throws NotFoundException {
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

    @PutMapping("/{id}")
    public ApiResponse<PostResponse> updateOne(@PathVariable Long id, @RequestBody PostRequest postDto) throws NotFoundException {
        return ApiResponse.ok(postService.update(id,postDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> removeOne(@PathVariable Long id) throws NotFoundException {
        postService.deleteById(id);
        return ApiResponse.ok("delete complete");
    }

}
