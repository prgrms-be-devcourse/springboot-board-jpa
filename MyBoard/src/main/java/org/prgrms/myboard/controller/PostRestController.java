package org.prgrms.myboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostCursorRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        return ResponseEntity.ok(postService.createPost(postCreateRequestDto));
    }

    @GetMapping
    public ResponseEntity<CursorResult<PostResponseDto>> getPostsByCursorId(@Valid @RequestBody
        PostCursorRequestDto postCursorRequestDto) {
        return ResponseEntity.ok(postService.findPostsByCursorId(postCursorRequestDto));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable("postId") Long postId,
                                                          @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return ResponseEntity.ok(postService.updateById(postId, postUpdateRequestDto));
    }
}
