package org.prgrms.myboard.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.OffsetResult;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
@Validated
public class PostRestController {
    private static final int PAGE_BASE_OFFSET = 1;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        return ResponseEntity.ok(postService.createPost(postCreateRequestDto));
    }

    @GetMapping("/cursor")
    public ResponseEntity<CursorResult<PostResponseDto>> getPostsByCursorId(
        @RequestParam(value = "cursorId") Long cursorId,
        @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(postService.findPostsByCursorPagination(cursorId, pageSize));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePostById(@PathVariable("postId") Long postId,
        @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return ResponseEntity.ok(postService.updateById(postId, postUpdateRequestDto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    // page가 totalpage보다 더 크게 들어오면 어떡하지?
    @GetMapping
    public ResponseEntity<OffsetResult<PostResponseDto>> getPostsByOffsetPagination(
        @Min(value = 1, message = "page값은 최소 1입니다.") @RequestParam(value = "page") int page,
        @Min(value = 1, message = "pageSize값은 최소 1입니다.")@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(postService.findPostsByOffsetPagination(
            PageRequest.of(page - PAGE_BASE_OFFSET, pageSize)));
    }

    @DeleteMapping("{postId}")
    public ResponseEntity deletePostById(@PathVariable("postId") Long postId) {
        postService.deleteById(postId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
