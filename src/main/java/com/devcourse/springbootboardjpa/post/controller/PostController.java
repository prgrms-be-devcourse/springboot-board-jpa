package com.devcourse.springbootboardjpa.post.controller;

import com.devcourse.springbootboardjpa.common.dto.CommonResponse;
import com.devcourse.springbootboardjpa.common.dto.ResponseDTO;
import com.devcourse.springbootboardjpa.common.dto.page.PageDTO;
import com.devcourse.springbootboardjpa.post.domain.Post;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseDTO> savePost(@RequestBody @Valid PostDTO.SaveRequest saveRequest) {
        Long postId = postService.savePost(saveRequest);

        return new ResponseEntity<>(
                CommonResponse.builder()
                        .status(HttpStatus.CREATED)
                        .message("게시물 저장 완료")
                        .data(postId)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ResponseDTO> findPost(@PathVariable Long id) {
        PostDTO.FindResponse findPostResponse = postService.findPost(id);

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .status(HttpStatus.OK)
                        .message("게시물 조회 완료")
                        .data(findPostResponse)
                        .build()
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDTO> findPosts(@RequestBody @Valid PageDTO.Request pageRequest) {
        PageDTO.Response<Post, PostDTO.FindResponse> posts = postService.findAllPostsPage(pageRequest);

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .status(HttpStatus.OK)
                        .message("게시물 조회 완료")
                        .data(posts)
                        .build()
        );
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<ResponseDTO> updatePost(@PathVariable Long id,
                                                  @RequestBody @Valid PostDTO.UpdateRequest postUpdateRequest) {

        Long postId = postService.updatePost(id, postUpdateRequest);

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .status(HttpStatus.OK)
                        .message("게시물 수정 완료")
                        .data(postId)
                        .build()
        );
    }
}
