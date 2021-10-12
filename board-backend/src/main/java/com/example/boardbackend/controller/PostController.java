package com.example.boardbackend.controller;

import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.repository.PostRepository;
import com.example.boardbackend.service.PostService;
import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // user id로 게시물 조회
    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable("id") Long createdBy){
        return ResponseEntity.ok(postService.findPostsByCreatedBy(createdBy));
    }

}
