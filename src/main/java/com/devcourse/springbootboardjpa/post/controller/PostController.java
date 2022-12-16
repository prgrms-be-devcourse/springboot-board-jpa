package com.devcourse.springbootboardjpa.post.controller;

import com.devcourse.springbootboardjpa.common.dto.CommonResponse;
import com.devcourse.springbootboardjpa.common.dto.ResponseDTO;
import com.devcourse.springbootboardjpa.post.domain.dto.PostDTO;
import com.devcourse.springbootboardjpa.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<ResponseDTO> savePost(@RequestBody PostDTO.SaveRequest saveRequest) {
        Long postId = postService.savePost(saveRequest);

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .message("게시물 저장 완료")
                        .data(postId)
                        .build()
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDTO> findPosts(Pageable pageable) {
        Page<PostDTO.FindResponse> posts = postService.findAllPostsPage(pageable);

        return ResponseEntity.ok(
                CommonResponse.builder()
                        .message("게시물 조회 완료")
                        .data(posts)
                        .build()
        );
    }
}
