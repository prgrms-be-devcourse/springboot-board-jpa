package com.example.board.controller;

import com.example.board.domain.service.LikeService;
import com.example.board.dto.like.LikePostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/likes")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts")
    public ResponseEntity<Long> createLikePost(@Valid @RequestBody LikePostRequestDto requestDto) {
        Long likePost = likeService.createLikePost(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(likePost);
    }
}
