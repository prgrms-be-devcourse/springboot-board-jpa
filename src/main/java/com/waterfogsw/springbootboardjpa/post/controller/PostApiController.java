package com.waterfogsw.springbootboardjpa.post.controller;

import com.waterfogsw.springbootboardjpa.post.controller.dto.PostAddRequest;
import com.waterfogsw.springbootboardjpa.post.controller.dto.PostResponse;
import com.waterfogsw.springbootboardjpa.post.controller.dto.PostUpdateRequest;
import com.waterfogsw.springbootboardjpa.post.service.PostService;
import com.waterfogsw.springbootboardjpa.post.util.PostConverter;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("api/v1/posts")
public class PostApiController {

    private final PostConverter postConverter;
    private final PostService postService;

    public PostApiController(PostConverter postConverter, PostService postService) {
        this.postConverter = postConverter;
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@RequestBody @Valid PostAddRequest postAddRequest) {
        final var post = postConverter.toEntity(postAddRequest);
        postService.addPost(post);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(
            @PathVariable @Positive long id,
            @RequestBody @Valid PostUpdateRequest postUpdateRequest
    ) {
        final var post = postConverter.toEntity(postUpdateRequest);
        postService.updatePost(id, post);
    }

    @GetMapping("{id}")
    public PostResponse getOne(@PathVariable @Positive long id) {
        final var post = postService.getOne(id);
        return postConverter.toDto(post);
    }

    @GetMapping
    public List<PostResponse> getAll() {
        final var posts = postService.getAll();
        return posts.stream()
                .map(postConverter::toDto)
                .collect(Collectors.toList());
    }
}
