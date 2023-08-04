package com.kdt.devcourse.module.post.presentation;

import com.kdt.devcourse.global.ApiResponse;
import com.kdt.devcourse.module.post.application.PostService;
import com.kdt.devcourse.module.post.application.dto.PostResponse;
import com.kdt.devcourse.module.post.presentation.dto.PostRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final String BASE_URI = "/posts";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @ResponseStatus(OK)
    public ApiResponse<Page<PostResponse>> findAll(@PageableDefault(sort = "id") Pageable pageable) {
        Page<PostResponse> responses = postService.findAll(pageable);
        return new ApiResponse<>(responses);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ApiResponse<PostResponse> findOneById(@PathVariable Long id) {
        PostResponse postResponse = postService.getPostResponse(id);
        return new ApiResponse<>(postResponse);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ApiResponse<URI> create(@RequestBody @Valid PostRequest request) {
        Long postId = postService.create(request.title(), request.content());
        URI uri = URI.create(BASE_URI + "/" + postId);
        return new ApiResponse<>(uri);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public ApiResponse<Void> update(@PathVariable Long id,
                                    @RequestBody @Valid PostRequest request) {
        postService.update(id, request.title(), request.content());
        return new ApiResponse<>();
    }
}
