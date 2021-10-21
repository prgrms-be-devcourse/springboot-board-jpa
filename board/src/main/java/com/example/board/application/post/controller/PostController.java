package com.example.board.application.post.controller;

import com.example.board.application.ApiResponse;
import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.post.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ApiResponse<String> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandle(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping
    public ApiResponse<Long> save(
            @Valid
            @RequestBody PostDto postDto
    ) {
        return ApiResponse.ok(postService.save(postDto));
    }

    @PostMapping("/{id}")
    public ApiResponse<Long> update(
            @PathVariable Long id,
            @Valid
            @RequestBody PostDto postDto
    ) throws NotFoundException {
        return ApiResponse.ok(postService.update(id, postDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getOne(
            @PathVariable Long id
    ) throws NotFoundException {
        return ApiResponse.ok(postService.findOne(id));
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> getAll(
            Pageable pageable
    ) {
        return ApiResponse.ok(postService.findPosts(pageable));
    }
}
