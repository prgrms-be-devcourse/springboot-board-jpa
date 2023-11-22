package com.example.board.controller;

import com.example.board.dto.PostDetailResponseDto;
import com.example.board.dto.PostDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.PostUpdateDto;
import com.example.board.exception.BindingException;
import com.example.board.response.Response;
import com.example.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.board.util.Validation.bindChecking;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping
    public Response<Long> save(@RequestBody @Validated PostDto postDto, BindingResult bindingResult) {
        List<String> bindingErrors = bindChecking(bindingResult);
        if (!bindingErrors.isEmpty()) {
            throw new BindingException(bindingErrors);
        }
        return Response.success(postService.save(postDto));
    }

    @GetMapping
    public Response<Page<PostResponseDto>> readAllPost(
            @PageableDefault Pageable pageable
    ) {
        return Response.success(postService.readAllPost(pageable));
    }

    @GetMapping("/{postId}")
    public Response<PostDetailResponseDto> readDetailPost(@PathVariable Long postId) {
        return Response.success(postService.readPostDetail(postId));
    }

    // TODO: 이후 로그인 정보로 유저 일치 확인하기(변경 예정)
    @PatchMapping("/{postId}")
    public Response<Long> update(
            @PathVariable Long postId,
            @RequestBody @Validated PostUpdateDto postUpdateDto
    ) {
        return Response.success(postService.update(postId, postUpdateDto, postUpdateDto.userId()));
    }
}
