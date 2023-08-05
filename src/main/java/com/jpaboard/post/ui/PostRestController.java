package com.jpaboard.post.ui;

import com.jpaboard.ApiResponse;
import com.jpaboard.post.application.PostService;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.post.ui.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> findPostAll(Pageable pageable) {
        return ApiResponse.ok(postService.findPostAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> findPost(@PathVariable long id) {
        PostDto postDto = postService.findPost(id);
        return ApiResponse.ok(postDto);
    }

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostDto postDto) {
        Long postId = postService.createPost(postDto);
        return ApiResponse.ok(postId);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable long id,
                                        @RequestBody PostUpdateDto postUpdateDto) {
        Long postId = postService.updatePost(id, postUpdateDto);
        return ApiResponse.ok(postId);
    }
}
