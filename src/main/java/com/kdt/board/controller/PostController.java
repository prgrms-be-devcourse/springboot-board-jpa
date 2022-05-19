package com.kdt.board.controller;

import com.kdt.board.ApiResponse;
import com.kdt.board.dto.PostDTO;
import com.kdt.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/posts")
    public ApiResponse<Long> savePost(@RequestBody PostDTO postDTO) {
        Long id = postService.savePost(postDTO);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDTO>> getPages(Pageable pageable) {
        Page<PostDTO> pages = postService.findPostsByPage(pageable);
        return ApiResponse.ok(pages);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDTO> getPost(@PathVariable Long id) {
        return ApiResponse.ok(postService.findPostById(id));
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        return ApiResponse.ok(postService.updatePost(id, postDTO));
    }
}
