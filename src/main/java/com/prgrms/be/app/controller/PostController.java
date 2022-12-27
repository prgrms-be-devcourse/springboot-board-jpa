package com.prgrms.be.app.controller;

import com.prgrms.be.app.domain.dto.ApiResponse;
import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {

    private final PostService postService;

    //toDo : 생성
    @PostMapping()
    public ApiResponse<Long> save(@RequestBody @Valid PostDTO.CreateRequest createRequest) {
        Long postId = postService.createPost(createRequest);
        return ApiResponse.created(
                postId,
                ResponseMessage.CREATED);
    }

    //toDo : 단건 조회하는 메서드
    @GetMapping("/{id}")
    public ApiResponse<PostDTO.PostDetailResponse> getOne(@PathVariable Long id) {
        PostDTO.PostDetailResponse postDetailResponse = postService.findById(id);
        return ApiResponse.ok(
                postDetailResponse,
                ResponseMessage.FINDED_ONE);
    }

    //toDo : 페이징 조회하는 메서드
    @GetMapping()
    public ApiResponse<PostDTO.PostsResponse> getAll(@RequestBody PostDTO.PostPageRequest postPageRequest) {
        PostDTO.PostsResponse postPages = postService.findAll(postPageRequest.of());
        return ApiResponse.ok(
                postPages,
                ResponseMessage.FINDED_ALL
        );
    }

    //toDo : 수정
    @PatchMapping("/{id}")
    public ApiResponse<Long> update(
            @PathVariable Long id,
            @RequestBody @Valid PostDTO.UpdateRequest request) {
        Long postId = postService.updatePost(id, request);
        return ApiResponse.ok(
                postId,
                ResponseMessage.UPDATED);
    }
}
