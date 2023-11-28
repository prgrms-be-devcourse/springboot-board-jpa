package com.example.board.controller;
import com.example.board.dto.ApiResult;
import com.example.board.dto.PostDto;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService service;

    @GetMapping
    public ApiResult<Page<PostDto.Response>> findAll(@PageableDefault(page=0,size = 10) Pageable pageable) {
        Page<PostDto.Response> responses = service.findAll(pageable);
        return ApiResult.successOf(responses);
    }

    @GetMapping("/{postId}")
    public ApiResult<PostDto.Response> findById(@PathVariable Long postId) {
        PostDto.Response response = service.findById(postId);
        return ApiResult.successOf(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<PostDto.Response> save(@Validated @RequestBody PostDto.Request request) {
        PostDto.Response response = service.save(request);
        return ApiResult.successOf(response);
    }

    @PostMapping("/{postId}")
    public ApiResult<Void> update(@PathVariable Long postId,
                                  @Validated @RequestBody PostDto.Request request) {
        service.update(postId, request);
        return ApiResult.successOf();
    }
}
