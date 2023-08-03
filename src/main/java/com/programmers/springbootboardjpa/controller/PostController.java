package com.programmers.springbootboardjpa.controller;

import com.programmers.springbootboardjpa.dto.post.PostCreateRequest;
import com.programmers.springbootboardjpa.dto.post.PostResponse;
import com.programmers.springbootboardjpa.dto.post.PostUpdateRequest;
import com.programmers.springbootboardjpa.global.ApiResponse;
import com.programmers.springbootboardjpa.global.validate.ValidationSequence;
import com.programmers.springbootboardjpa.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Validated(ValidationSequence.class) @RequestBody PostCreateRequest postCreateRequest) {
        postService.save(postCreateRequest);
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> findAll(Pageable pageable) {
        List<PostResponse> postResponses = postService.findAll(pageable);

        return ApiResponse.ok(postResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findById(@PathVariable Long id) {
        PostResponse postResponse = postService.findById(id);

        return ApiResponse.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        postService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable Long id, @Validated(ValidationSequence.class) @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updateById(id, postUpdateRequest);
    }

}
