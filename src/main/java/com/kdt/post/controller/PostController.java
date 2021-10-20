package com.kdt.post.controller;

import com.kdt.common.http.ApiResponse;
import com.kdt.post.dto.PostControlRequestDto;
import com.kdt.post.dto.PostDto;
import com.kdt.post.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    private ApiResponse<String> exceptionHandling(Exception exception){
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ApiResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), errors);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandler(NotFoundException exception){
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping("/posts")
    public ApiResponse<Long> savePost(@Valid @RequestBody PostControlRequestDto saveRequest) throws NotFoundException {
        Long postId = postService.save(saveRequest);
        return ApiResponse.ok(postId);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getPost(@PathVariable Long id) throws NotFoundException {
        PostDto postDto = postService.find(id);
        return ApiResponse.ok(postDto);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAllPost(Pageable pageable){
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @PutMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(@PathVariable Long id, @Valid @RequestBody PostControlRequestDto updateRequest) throws NotFoundException {
        Long updatedPostID = postService.update(id, updateRequest);
        return ApiResponse.ok(updatedPostID);
    }

    @DeleteMapping("/posts/{id}")
    public ApiResponse<Long> deletePost(@PathVariable Long id, @Valid @RequestBody PostControlRequestDto removeRequest) throws NotFoundException {
        postService.delete(id, removeRequest);
        return ApiResponse.ok(id);
    }
}
