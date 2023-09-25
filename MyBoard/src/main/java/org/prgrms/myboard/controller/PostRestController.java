package org.prgrms.myboard.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.OffsetResult;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
@Validated
public class PostRestController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        return ResponseEntity.ok(postService.createPost(postCreateRequestDto));
    }

    @GetMapping("/cursor")
    public ResponseEntity<CursorResult<PostResponseDto>> getPostsByCursorId(
        @RequestParam(value = "cursorId", required = false) Long cursorId,
        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
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

    @GetMapping
    public ResponseEntity<OffsetResult<PostResponseDto>> getPostsByOffsetPagination(
        @Min(value = 1, message = "page값은 최소 1입니다.") @RequestParam(value = "page") int page,
        @Min(value = 1, message = "pageSize값은 최소 1입니다.") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(postService.findPostsByOffsetPagination(
            page, pageSize)
        );
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostResponseDto>> getPostsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(postService.findAllByUserId(userId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable("postId") Long postId) {
        postService.deleteById(postId);
        return ResponseEntity.ok().build();
    }

}
