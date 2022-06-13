package org.programmers.kdtboard.controller;

import javax.validation.Valid;

import org.programmers.kdtboard.controller.response.ApiResponse;
import org.programmers.kdtboard.dto.PostDto;
import org.programmers.kdtboard.dto.PostDto.Response;
import org.programmers.kdtboard.dto.PostDto.UpdateRequest;
import org.programmers.kdtboard.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Response> createPost(@RequestBody @Valid PostDto.CreateRequest postCreateRequest) {
        var postResponse = this.postService.create(postCreateRequest.title(), postCreateRequest.content(),
                postCreateRequest.userId());

        return ApiResponse.create(postResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<Response> findById(@PathVariable Long id) {
        var foundPost = this.postService.findById(id);

        return ApiResponse.ok(foundPost);
    }

    @GetMapping
    public ApiResponse<Page<Response>> findAll(Pageable pageable) {
        var posts = this.postService.findAll(pageable);

        return ApiResponse.ok(posts);
    }

    @PutMapping("/{id}")
    public ApiResponse<Response> update(@PathVariable Long id,
                                        @RequestBody @Valid UpdateRequest postUpdateRequest) {
        var updatePost = this.postService.update(id, postUpdateRequest.title(), postUpdateRequest.content());

        return ApiResponse.ok(updatePost);
    }
}
