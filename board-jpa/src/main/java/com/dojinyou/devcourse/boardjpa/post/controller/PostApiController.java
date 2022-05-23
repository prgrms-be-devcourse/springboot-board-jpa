package com.dojinyou.devcourse.boardjpa.post.controller;

import com.dojinyou.devcourse.boardjpa.post.controller.dto.PostCreateRequest;
import com.dojinyou.devcourse.boardjpa.post.service.PostService;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/posts")
public class PostApiController {
    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody @Valid PostCreateRequest postCreateRequest) {
        long createUserId = postCreateRequest.getUserId();
        PostCreateDto postCreateDto = new PostCreateDto.Builder().title(postCreateRequest.getTitle())
                                                                 .content(postCreateRequest.getContent())
                                                                 .build();
        postService.create(createUserId, postCreateDto);
    }
}
