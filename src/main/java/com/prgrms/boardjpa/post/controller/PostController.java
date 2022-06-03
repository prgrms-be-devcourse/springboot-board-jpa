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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private Response<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Response.fail(HttpStatus.BAD_REQUEST.value(), errors, "잘못된 요청입니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    private Response<String> handleException(Exception exception) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), "서버 오류");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{postId}")
    public Response<PostResDto> getOne(@PathVariable Long postId) {
        return Response.ok(postService.findOne(postId), "게시글 단건 조회 성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Page<PostResDto>> getAll(Pageable pageable) {
        return Response.ok(postService.findPosts(pageable), "게시글 다건 조회 성공");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<PostResDto> save(@RequestBody @Valid PostReqDto postReqDto) {
        return Response.ok(postService.save(postReqDto), "게시글 생성 성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{postId}")
    public Response<PostResDto> update(@PathVariable Long postId, @RequestBody @Valid PostUpdateDto postUpdateDto) {
        return Response.ok(postService.update(postId, postUpdateDto), "게시글 수정 성공");
    }
}
