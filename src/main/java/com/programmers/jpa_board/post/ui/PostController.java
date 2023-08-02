package com.programmers.jpa_board.post.ui;

import com.programmers.jpa_board.global.ApiResponse;
import com.programmers.jpa_board.post.application.PostService;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ApiResponse<PostResponse> save(@RequestBody CreatePostRequest request) {
        PostResponse response = postService.create(request);

        return ApiResponse.created(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findById(@PathVariable("id") Long postId) {
        PostResponse response = postService.findById(postId);
        return ApiResponse.ok(response);
    }
}
