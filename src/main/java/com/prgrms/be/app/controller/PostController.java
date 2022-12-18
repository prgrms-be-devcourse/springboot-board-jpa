package com.prgrms.be.app.controller;

import com.prgrms.be.app.domain.dto.PostDTO;
import com.prgrms.be.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class PostController {

    /**
     * - 게시글 조회
     *     - 페이징 조회 (GET "/posts")
     *     - 단건 조회 (GET "/posts/{id}")
     * - 게시글 작성 (POST "/posts")
     * - 게시글 수정 (POST "/posts/{id}")
     */
    private final PostService postService;

    //toDo : 생성
    @PostMapping("/posts")
    public ResponseEntity<Object> save(@RequestBody PostDTO.CreateRequest createRequest) {
        Long postId = postService.createPost(createRequest);
//        ResponseEntity.created(URI.create("/post/"))
        return ResponseEntity.created(URI.create("/"+postId)).build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    //toDo : 페이징 조회하는 메서드
    @GetMapping("/posts")
    public PostDTO.PostsWithPaginationResponse getAll(Pageable pageable) {
        return postService.findAll(pageable);
    }

    //toDo : 단건 조회하는 메서드
    @GetMapping("/posts/{id}")
    public PostDTO.PostDetailResponse getOne(
            @PathVariable Long id) {

        PostDTO.PostDetailResponse postDetailResponse = postService.findById(id);

        return postDetailResponse;
    }

    //toDo : 수정
    @PostMapping("/posts/{id}")
    public ResponseEntity<Object> update(
            @PathVariable Long id,@RequestBody PostDTO.UpdateRequest updateRequest) {
        postService.updatePost(updateRequest,id);
        return ResponseEntity.noContent().build();
//        throw new UnsupportedOperationException();
    }
}
