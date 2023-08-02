package com.jpaboard.post.ui;

import com.jpaboard.ApiResponse;
import com.jpaboard.post.application.PostService;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.post.ui.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PostRestController {
    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> findPostAll(Pageable pageable) {
        return ApiResponse.ok(postService.findPostAll(pageable));
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getMember(@PathVariable long id) {
        PostDto postDto = postService.getMember(id);
        return ApiResponse.ok(postDto);
    }

    @PostMapping("/posts")
    public ApiResponse<Long> createPost(@RequestBody PostDto postDto) {
        Long postId = postService.createPost(postDto);
        return ApiResponse.ok(postId);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(@PathVariable long id,
                                        @RequestBody PostUpdateDto postUpdateDto) {
        Long postId = postService.updatePost(id, postUpdateDto);
        return ApiResponse.ok(postId);
    }
}
