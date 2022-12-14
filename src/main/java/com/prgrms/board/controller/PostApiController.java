package com.prgrms.board.controller;

import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostResponseDto;
import com.prgrms.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody PostCreateDto createDto) {
        Long savedPostId = postService.register(createDto);

        return new ResponseEntity<>(savedPostId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto responseDto = postService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
