package com.prgrms.springbootboardjpa.controller;

import com.prgrms.springbootboardjpa.dto.PostRequest;
import com.prgrms.springbootboardjpa.dto.PostResponse;
import com.prgrms.springbootboardjpa.facade.PostFacade;
import com.prgrms.springbootboardjpa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/")
@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    private final PostFacade postFacade;

    @GetMapping("posts/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getOne(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.of(postService.getOne(id)));
    }

    @GetMapping("posts")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.of(postService.getAll(pageable)));
    }

    @PostMapping("posts")
    public ResponseEntity<ApiResponse<PostResponse>> save(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(ApiResponse.of(postFacade.save(postRequest)));
    }

    @PostMapping("posts/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(@PathVariable long id, @RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(ApiResponse.of(postService.update(id, postRequest)));
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        postService.delete(id);
        return ResponseEntity.ok(ApiResponse.of("성공적으로 삭제 됐습니다."));
    }
}
