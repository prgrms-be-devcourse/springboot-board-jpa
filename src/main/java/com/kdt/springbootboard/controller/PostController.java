package com.kdt.springbootboard.controller;


import com.kdt.springbootboard.dto.post.PostCreateRequest;
import com.kdt.springbootboard.dto.post.PostResponse;
import com.kdt.springbootboard.dto.post.PostUpdateRequest;
import com.kdt.springbootboard.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    
    // Todo : ResponseEntity 찾아보기
    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandle(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping("/api/v1/posts")
    public ApiResponse<Long> insertPost(@RequestBody PostCreateRequest request) throws NotFoundException {
        Long id = postService.insert(request);
        return ApiResponse.ok(id);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(postService.findById(id));
    }

    @GetMapping("/api/v1/posts")
    public ApiResponse<Page<PostResponse>> getAllPost(Pageable pageable) {
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @GetMapping("/api/v1/posts/user/{id}")
    public ApiResponse<Page<PostResponse>> getAllPostByUserId(@PathVariable Long id, Pageable pageable) throws NotFoundException {
        return ApiResponse.ok(postService.findAllByUser(id, pageable));
    }

    @PutMapping("/api/v1/posts") // Todo : PUT or PATCH ?
    public ApiResponse<PostResponse> updatePost(@RequestBody PostUpdateRequest request) throws NotFoundException {
        return ApiResponse.ok(postService.update(request));
    }

    @DeleteMapping("/api/v1/posts/{id}") // Todo : delete Response 만들어야 함?
    public ApiResponse<Long> deletePost(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(postService.delete(id));
    }
}
