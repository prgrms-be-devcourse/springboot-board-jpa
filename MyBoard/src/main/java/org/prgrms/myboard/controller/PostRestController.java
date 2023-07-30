package org.prgrms.myboard.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostCursorRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostRestController {
    private static final int DEFAULT_SIZE = 10;
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        return ResponseEntity.ok(postService.createPost(postCreateRequestDto));
    }

    // GetMapping이라 RequestBody없음
    @GetMapping
    public ResponseEntity<CursorResult<PostResponseDto>> getAllPostsByCursorId(@Valid @RequestBody
        PostCursorRequestDto postCursorRequestDto) {
        int pageSize = postCursorRequestDto.pageSize() == null ? DEFAULT_SIZE
            : postCursorRequestDto.pageSize();
        return ResponseEntity.ok(postService.findAllByCursorId(postCursorRequestDto.cursorId(),
            PageRequest.of(0, pageSize)));
    }
}
