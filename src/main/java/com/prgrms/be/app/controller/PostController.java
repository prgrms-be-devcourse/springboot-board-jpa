package com.prgrms.be.app.controller;

import com.prgrms.be.app.domain.dto.ApiResponse;
import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final static Pageable pageSetUp = PageRequest.of(0, 5);

    //toDo : 생성
    @PostMapping("/posts")
    public ResponseEntity<ApiResponse> save(@RequestBody PostDTO.CreateRequest createRequest) {
        Long postId = postService.createPost(createRequest);
        return ResponseEntity.ok(ApiResponse.ok(
                postId,
                "게시물 생성 완료"
        ));
    }

    //toDo : 단건 조회하는 메서드
    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse> getOne(
            @PathVariable Long id) {
        PostDTO.PostDetailResponse postDetailResponse = postService.findById(id);
        return ResponseEntity.ok(
                ApiResponse.ok(
                        postDetailResponse,
                        "게시물 단건 조회 완료"
                )
        );
    }

    //toDo : 페이징 조회하는 메서드
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse> getAll() {
        Page<PostDTO.PostsResponse> postPages = postService.findAll(pageSetUp);
        return ResponseEntity.ok(
                ApiResponse.ok(
                        postPages,
                        "게시물 페이지 조회 완료"
                )
        );
    }

    //toDo : 수정
    @PostMapping("/posts/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @RequestBody PostDTO.UpdateRequest request) {
        Long postId = postService.updatePost(id, request);
        return ResponseEntity.ok(
                ApiResponse.ok(
                        postId,
                        "게시글 수정 완료"
                ));
    }
}
