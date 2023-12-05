package com.prgrms.dev.springbootboardjpa.controller;

import com.prgrms.dev.springbootboardjpa.dto.PostCreateRequestDto;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostUpdateRequestDto;
import com.prgrms.dev.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    //단건 조회 -> find
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Long id) {
        PostDto postDto = postService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    //페이지 조회 -> get
    @GetMapping
    public ResponseEntity<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> postDto = postService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    //생성
    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        PostDto postDto = postService.create(postCreateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    //수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> update(@RequestBody PostUpdateRequestDto postUpdateRequestDto, @PathVariable Long id) {
        PostDto update = postService.update(postUpdateRequestDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(update);
    }

    // 서로 다른 리턴 값을 줄 때 컴파일 시점에서 잡아줄 수 있는 에러 ...
}
