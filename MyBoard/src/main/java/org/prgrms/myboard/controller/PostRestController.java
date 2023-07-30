package org.prgrms.myboard.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.OffsetResult;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostCursorRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/posts")
@RestController
@RequiredArgsConstructor
@Validated
public class PostRestController {
    private static final int PAGE_BASE_OFFSET = 1;
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        return ResponseEntity.ok(postService.createPost(postCreateRequestDto));
    }

    @GetMapping
    public ResponseEntity<CursorResult<PostResponseDto>> getPostsByCursorId(@Valid @RequestBody
        PostCursorRequestDto postCursorRequestDto) {
        return ResponseEntity.ok(postService.findPostsByCursorPagination(postCursorRequestDto));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable("postId") Long postId,
        @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return ResponseEntity.ok(postService.updateById(postId, postUpdateRequestDto));
    }

    // page가 totalpage보다 더 크게 들어오면 어떡하지?
    @GetMapping("/{page}")
    public ResponseEntity<OffsetResult<PostResponseDto>> getPostsByOffsetPagination(
        @PathVariable("page") @Min(value = 1, message = "page offset은 최소 1이여야 합니다.")
        int page,
        @RequestParam("size") @Min(value = 1, message = "page Size는 최소 1이여야 합니다.")
        int pageSize) {
        return ResponseEntity.ok(postService.findPostsByOffsetPagination(
            PageRequest.of(page - PAGE_BASE_OFFSET, pageSize)));
    }
}
