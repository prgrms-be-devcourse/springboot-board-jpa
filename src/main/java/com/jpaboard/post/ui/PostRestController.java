package com.jpaboard.post.ui;

import com.jpaboard.ApiResponse;
import com.jpaboard.post.application.PostService;
import com.jpaboard.post.ui.dto.PostDto;
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
    public ApiResponse<Page<PostDto.Response>> findPostAll(@RequestBody Map<String, Integer> param) {
        int page = param.get("page");
        int size = param.get("size");
        return ApiResponse.ok(postService.findPostAll(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto.Response> findPost(@PathVariable long id) {
        PostDto.Response response = postService.findPost(id);
        return ApiResponse.ok(response);
    }

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostDto.Request request) {
        Long postId = postService.createPost(request);
        return ApiResponse.ok(postId);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable long id,
                                        @RequestBody PostDto.PostUpdateRequest postUpdateRequest) {
        Long postId = postService.updatePost(id, postUpdateRequest);
        return ApiResponse.ok(postId);
    }
}
