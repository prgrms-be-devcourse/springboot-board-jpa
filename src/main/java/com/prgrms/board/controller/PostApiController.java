package com.prgrms.board.controller;

import com.prgrms.board.dto.*;
import com.prgrms.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final PostService postService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> register(@RequestBody @Valid PostCreateDto createDto) {
        Long savedPostId = postService.register(createDto);

        return ApiResponse.created(savedPostId);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostResponseDto> findById(@PathVariable Long id) {

        PostResponseDto responseDto = postService.findById(id);
        return ApiResponse.ok(responseDto);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CursorResult> findAll(Long cursorId, Integer size) {
        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        CursorResult cursorResult = postService.findAll(cursorId, PageRequest.of(0, size));
        return ApiResponse.ok(cursorResult);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Long> update(@RequestBody @Valid PostUpdateDto postUpdateDto) {
        Long updatedPostId = postService.update(postUpdateDto);

        return ApiResponse.ok(updatedPostId);
    }
}
