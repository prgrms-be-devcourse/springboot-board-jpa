package com.kdt.devcourse.module.post.presentation;

import com.kdt.devcourse.global.ApiResponse;
import com.kdt.devcourse.module.post.application.PostService;
import com.kdt.devcourse.module.post.presentation.dto.CreateOrUpdatePost;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<Page<CreateOrUpdatePost.Response>> findAll(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CreateOrUpdatePost.Response> responses = postService.findAll(pageRequest);
        return new ApiResponse<>(responses);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public ApiResponse<CreateOrUpdatePost.Response> findOneById(@PathVariable Long id) {
        CreateOrUpdatePost.Response postResponse = postService.getPostResponse(id);
        return new ApiResponse<>(postResponse);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ApiResponse<Void> create(@RequestBody @Valid CreateOrUpdatePost.Request request) {
        postService.create(request.title(), request.content());
        return new ApiResponse<>();
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public ApiResponse<Void> update(@PathVariable Long id,
                                    @RequestBody @Valid CreateOrUpdatePost.Request request) {
        postService.update(id, request.title(), request.content());
        return new ApiResponse<>();
    }
}
