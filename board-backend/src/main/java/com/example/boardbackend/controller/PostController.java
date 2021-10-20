package com.example.boardbackend.controller;

import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.request.UpdatePostRequest;
import com.example.boardbackend.dto.request.UpdateViewRequest;
import com.example.boardbackend.dto.response.BoardResponse;
import com.example.boardbackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;

    // 게시물 생성
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid  @RequestBody PostDto postDto) {
        PostDto response = postService.savePost(postDto);
        return ResponseEntity.ok(response);
    }

    // 게시물 전체 조회 (페이징)
    @GetMapping
    public ResponseEntity<Page<BoardResponse>> getAllPosts(Pageable pageable){
        Page<BoardResponse> response = postService.findPostsAll(pageable);
        return ResponseEntity.ok(response);
    }

    // 총 게시글 수 조회
    @GetMapping("/total")
    public ResponseEntity<Long> getTotalCount(){
        Long response = postService.countPostsAll();
        return ResponseEntity.ok(response);
    }

    // user id로 게시물 조회
    @GetMapping("/user/{id}")
    public ResponseEntity<List<BoardResponse>> getUserPosts(@PathVariable("id") Long userId) {
        List<BoardResponse> response = postService.findPostsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    // post id로 게시물 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) {
        PostDto response = postService.findPostById(id);
        return ResponseEntity.ok(response);
    }

    // 게시물 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdatePostRequest updatePostRequest
    ) {
        PostDto response = postService.updatePostById(id, updatePostRequest);
        return ResponseEntity.ok(response);
    }

    // 조회수 업데이트
    @PatchMapping("/{id}/view")
    public ResponseEntity<Long> updateView(
            @PathVariable("id") Long id,
            @RequestBody UpdateViewRequest updateViewRequest
    ) {
        Long response = postService.updateViewById(id, updateViewRequest);
        return ResponseEntity.ok(response);
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable("id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

}
