package org.prgms.board.post.controller;

import org.prgms.board.common.response.ApiResponse;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.post.service.PostService;
import org.prgms.board.user.dto.UserIdRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> getAllPost(Pageable pageable) {
        return ApiResponse.toResponse(postService.getAllPost(pageable));
    }

    @GetMapping("/users")
    public ApiResponse<Page<PostResponse>> getAllPostByUser(Pageable pageable, @RequestBody UserIdRequest userIdRequest) {
        return ApiResponse.toResponse(postService.getAllPostByUser(pageable, userIdRequest));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getOnePost(@PathVariable Long id) {
        return ApiResponse.toResponse(postService.getOnePost(id));
    }

    @PostMapping
    public ApiResponse<Long> addPost(@RequestBody @Valid PostRequest postRequest) {
        return ApiResponse.toResponse(postService.addPost(postRequest));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> modifyPost(@PathVariable Long id, @RequestBody @Valid PostRequest postRequest) {
        return ApiResponse.toResponse(postService.modifyPost(id, postRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Integer> removePost(@PathVariable Long id, @RequestBody UserIdRequest userIdRequest) {
        postService.removePost(id, userIdRequest);
        return ApiResponse.toResponse(1);
    }

}
