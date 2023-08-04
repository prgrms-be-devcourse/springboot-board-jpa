package com.programmers.springbootboardjpa.domain.post.controller;

import com.programmers.springbootboardjpa.domain.post.dto.PostCreateRequestDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostResponseDto;
import com.programmers.springbootboardjpa.domain.post.dto.PostUpdateRequestDto;
import com.programmers.springbootboardjpa.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto) {
        PostResponseDto postResponseDto = postService.create(postCreateRequestDto);

        return ResponseEntity.ok().body(postResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findAll(@PageableDefault(sort = "id", size = 10, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PostResponseDto> postResponseDtos = postService.findAll(pageable);

        return ResponseEntity.ok().body(postResponseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.findById(id);

        return ResponseEntity.ok().body(postResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id, @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto) {
        PostResponseDto postResponseDto = postService.update(id, postUpdateRequestDto);

        return ResponseEntity.ok().body(postResponseDto);
    }
}
