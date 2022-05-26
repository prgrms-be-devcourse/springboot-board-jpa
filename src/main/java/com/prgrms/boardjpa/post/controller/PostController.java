package com.prgrms.boardjpa.post.controller;

import com.prgrms.boardjpa.Response;
import com.prgrms.boardjpa.post.dto.PostReqDto;
import com.prgrms.boardjpa.post.dto.PostResDto;
import com.prgrms.boardjpa.post.dto.PostUpdateDto;
import com.prgrms.boardjpa.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Response<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(Response.fail(HttpStatus.BAD_REQUEST.value(), errors, "잘못된 요청입니다."));
    }

    @ExceptionHandler
    private ResponseEntity<Response<String>> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), "서버 오류"));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Response<PostResDto>> getOne(@PathVariable Long postId) {
        return ResponseEntity.ok(Response.ok(postService.findOne(postId), "게시글 단건 조회 성공"));
    }

    @GetMapping
    public ResponseEntity<Response<Page<PostResDto>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(Response.ok(postService.findPosts(pageable), "게시글 다건 조회 성공"));
    }

    @PostMapping
    public ResponseEntity<Response<PostResDto>> save(@RequestBody @Valid PostReqDto postReqDto) {
        return ResponseEntity.ok(Response.ok(postService.save(postReqDto), "게시글 생성 성공"));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Response<PostResDto>> update(@PathVariable Long postId, @RequestBody @Valid PostUpdateDto postUpdateDto) {
        return ResponseEntity.ok(Response.ok(postService.update(postId, postUpdateDto), "게시글 수정 성공"));
    }
}
