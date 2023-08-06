package com.jpaboard.post.ui;

import com.jpaboard.ApiResponse;
import com.jpaboard.post.application.PostService;
import com.jpaboard.post.ui.dto.PostResponse;
import com.jpaboard.post.ui.dto.PostUpdateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> findPostAll(@RequestBody Map<String, Integer> param) {
        int page = param.get("page");
        int size = param.get("size");
        return ApiResponse.ok(postService.findPostAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findPost(@PathVariable long id) {
        PostResponse postResponse = postService.findPost(id);
        return ApiResponse.ok(postResponse);
    }

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostResponse postResponse) {
        Long postId = postService.createPost(postResponse);
        return ApiResponse.ok(postId);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable long id,
                                        @RequestBody PostUpdateResponse postUpdateResponse) {
        Long postId = postService.updatePost(id, postUpdateResponse);
        return ApiResponse.ok(postId);
    }
}
