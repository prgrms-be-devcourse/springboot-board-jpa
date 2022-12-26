package com.prgrms.board.controller;

import com.prgrms.board.dto.*;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.dto.request.PostRequestDto;
import com.prgrms.board.dto.request.PostUpdateDto;
import com.prgrms.board.dto.response.ApiResponse;
import com.prgrms.board.dto.response.PostResponseDto;
import com.prgrms.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/{postId}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostResponseDto> findById(@PathVariable Long postId) {

        PostResponseDto responseDto = postService.findById(postId);
        return ApiResponse.ok(responseDto);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CursorResult> findAll(@Valid PostRequestDto postRequestDto) {
        CursorResult cursorResult = postService.findAll(postRequestDto.getCursorId(), PageRequest.of(0, postRequestDto.getSize()));
        return ApiResponse.ok(cursorResult);
    }

    @PutMapping(value = "/{postId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Long> update(@PathVariable Long postId, @RequestBody @Valid PostUpdateDto postUpdateDto) {
        Long updatedPostId = postService.update(postId, postUpdateDto);

        return ApiResponse.ok(updatedPostId);
    }
}
