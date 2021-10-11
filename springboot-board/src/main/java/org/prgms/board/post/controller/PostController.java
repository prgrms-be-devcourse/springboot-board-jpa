package org.prgms.board.post.controller;

import org.prgms.board.common.response.ApiResponse;
import org.prgms.board.post.dto.PostRequest;
import org.prgms.board.post.dto.PostResponse;
import org.prgms.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> getAllPost(Pageable pageable, @PathVariable Optional<Long> userId) {
        if (userId.isPresent()) {
            return ApiResponse.toResponse(postService.getAllPostByUser(pageable, userId.get()));
        } else {
            return ApiResponse.toResponse(postService.getAllPost(pageable));
        }
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getOnePost(@PathVariable Long postId) {
        return ApiResponse.toResponse(postService.getOnePost(postId));
    }

    @PostMapping("/{userId}")
    public ApiResponse<Long> addPost(@PathVariable Long userId, @RequestBody @Valid PostRequest postRequest) {
        return ApiResponse.toResponse(postService.addPost(userId, postRequest));
    }

    @PutMapping("/{userId}/{postId}")
    public ApiResponse<Long> modifyPost(@PathVariable Long userId, @PathVariable Long postId, @RequestBody @Valid PostRequest postRequest) {
        return ApiResponse.toResponse(postService.modifyPost(userId, postId, postRequest));
    }

    @DeleteMapping("/{userId}/{postId}")
    public ApiResponse<Integer> removePost(@PathVariable Long userId, @PathVariable Long postId) {
        postService.removePost(userId, postId);
        return ApiResponse.toResponse(1);
    }

}
