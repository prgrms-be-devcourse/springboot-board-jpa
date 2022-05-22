package com.will.jpapractice.domain.post.api;

import com.will.jpapractice.domain.post.dto.PostRequest;
import com.will.jpapractice.domain.post.dto.PostResponse;
import com.will.jpapractice.global.common.response.ApiResponse;
import com.will.jpapractice.domain.post.application.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<Page<PostResponse>> getAll(Pageable pageable) {
        return ApiResponse.ok(postService.findPosts(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) throws NotFoundException{
        return ApiResponse.ok(postService.findPost(id));
    }

    @PostMapping
    public ApiResponse<Long> save(@NotNull @RequestHeader(AUTHORIZATION) Long userId, @RequestBody PostRequest postRequest) throws NotFoundException {
        return ApiResponse.ok(postService.save(userId, postRequest));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> update(@PathVariable Long id, @Valid @RequestBody PostRequest postRequest) throws NotFoundException {
        return ApiResponse.ok(postService.update(id, postRequest));
    }
}
