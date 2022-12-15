package com.prgrms.board.controller;

import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostResponseDto;
import com.prgrms.board.dto.PostUpdateDto;
import com.prgrms.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> register(@RequestBody @Valid PostCreateDto createDto) {
        Long savedPostId = postService.register(createDto);

        return new ResponseEntity<>(savedPostId, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto responseDto = postService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CursorResult> findAll(Long cursorId, Integer size) {
        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        CursorResult cursorResult = postService.findAll(cursorId, PageRequest.of(0, size));
        return new ResponseEntity<>(cursorResult, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody @Valid PostUpdateDto postUpdateDto) {
        Long updatedPostId = postService.update(postUpdateDto);

        return new ResponseEntity<>(updatedPostId, HttpStatus.OK);
    }
}
