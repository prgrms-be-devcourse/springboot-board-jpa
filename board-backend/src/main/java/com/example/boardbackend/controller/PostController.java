package com.example.boardbackend.controller;

import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.converter.ResponseConverter;
import com.example.boardbackend.dto.request.UpdateViewRequest;
import com.example.boardbackend.dto.response.BoardResponse;
import com.example.boardbackend.repository.PostRepository;
import com.example.boardbackend.service.PostService;
import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;
    private final ResponseConverter responseConverter;

    // 게시물 생성
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto response = postService.savePost(postDto);
        return ResponseEntity.ok(response);
    }

    // user id로 게시물 조회
    @GetMapping("/user/{id}")
    public ResponseEntity<List<BoardResponse>> getUserPosts(@PathVariable("id") Long createdBy) {
        List<BoardResponse> response = postService.findPostsByCreatedBy(createdBy).stream()
                .map(postDto -> responseConverter.convertToBoard(postDto))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // post id로 게시물 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") Long id) {
        PostDto response = postService.findPostById(id);
        return ResponseEntity.ok(response);
    }

    // 조회수 업데이트
    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateView(
            @PathVariable("id") Long id,
            @RequestBody UpdateViewRequest updateViewRequest
            ) {
        Long response = postService.updateView(id, updateViewRequest.getNewView());
        return ResponseEntity.ok(response);
    }

}
