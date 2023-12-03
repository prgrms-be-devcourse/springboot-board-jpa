package com.programmers.springbootboardjpa.controller;

import com.programmers.springbootboardjpa.dto.PostControllerCreateRequestDto;
import com.programmers.springbootboardjpa.dto.PostControllerUpdateRequestDto;
import com.programmers.springbootboardjpa.dto.PostServiceRequestDto;
import com.programmers.springbootboardjpa.dto.PostServiceResponseDto;
import com.programmers.springbootboardjpa.exception.ErrorMsg;
import com.programmers.springbootboardjpa.exception.PostNotFoundException;
import com.programmers.springbootboardjpa.exception.UserNotFoundException;
import com.programmers.springbootboardjpa.model.CommonResult;
import com.programmers.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostRestController {
    private final PostService postService;

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> userNotFoundException(UserNotFoundException e) {
        return CommonResult.getFailResult(ErrorMsg.USER_NOT_FOUND.getCode(), e.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResult<String> postNotFoundException(PostNotFoundException e) {
        return CommonResult.getFailResult(ErrorMsg.POST_NOT_FOUND.getCode(), e.getMessage());
    }

    @GetMapping
    public CommonResult<Page<PostServiceResponseDto>> getAllPost(Pageable pageable) {
        return CommonResult.getResult(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public CommonResult<PostServiceResponseDto> getPostById(@PathVariable Long id) {
        return CommonResult.getResult(postService.findPostById(id));
    }

    @PostMapping
    public CommonResult<PostServiceResponseDto> createPost(@RequestBody PostControllerCreateRequestDto requestDto) {
        return CommonResult.getResult(postService.create(PostServiceRequestDto.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .userId(requestDto.getUserId())
                .build()));
    }

    @PostMapping("/{id}")
    public CommonResult<String> updatePost(@PathVariable Long id, @RequestBody PostControllerUpdateRequestDto requestDto) {
        postService.update(id, PostServiceRequestDto.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build());
        return CommonResult.getSuccessResult();
    }
}
