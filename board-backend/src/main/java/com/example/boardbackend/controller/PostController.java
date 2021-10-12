package com.example.boardbackend.controller;

import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.repository.PostRepository;
import com.example.boardbackend.service.PostService;
import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;

    // 게시물 생성
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.savePost(postDto));
    }

}
