package com.programmers.jpa_board.post.ui;

import com.programmers.jpa_board.global.ApiResponse;
import com.programmers.jpa_board.post.application.PostService;
import com.programmers.jpa_board.post.domain.dto.PostDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ApiResponse<PostDto.CommonResponse> save(@RequestBody @Valid PostDto.CreatePostRequest request) {
        PostDto.CommonResponse response = postService.save(request);

        return ApiResponse.created(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto.CommonResponse> getOne(@PathVariable("id") Long id) {
        PostDto.CommonResponse response = postService.getOne(id);

        return ApiResponse.ok(response);
    }

    @GetMapping
    public ApiResponse<Page<PostDto.CommonResponse>> getPage(Pageable pageable) {
        Page<PostDto.CommonResponse> responses = postService.getPage(pageable);

        return ApiResponse.ok(responses);
    }

    @PutMapping("/{id}")
    public ApiResponse<PostDto.CommonResponse> update(@PathVariable("id") Long id, @RequestBody @Valid PostDto.UpdatePostRequest request) {
        PostDto.CommonResponse response = postService.update(id, request);

        return ApiResponse.ok(response);
    }
}
