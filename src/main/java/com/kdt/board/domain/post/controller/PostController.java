package com.kdt.board.domain.post.controller;

import com.kdt.board.domain.post.dto.PostRequestDto;
import com.kdt.board.domain.post.dto.PostResponseDto;
import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> savePost(@RequestBody PostRequestDto requestDto) {
        Post post = postService.save(requestDto);
        return ResponseEntity.ok(new PostResponseDto(post));
    }
}
