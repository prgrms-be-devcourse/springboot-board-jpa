package com.kdt.springbootboardjpa.controller;

import com.kdt.springbootboardjpa.service.PostService;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import com.kdt.springbootboardjpa.domain.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class RestPostController {

    private final PostService postService;

    @GetMapping("/posts/{id}")
    public PostDTO getPost(@PathVariable("id") long id) {
        return postService.findPost(id);
    }

    @GetMapping("/posts")
    public Page<PostDTO> getPostList(
            @PageableDefault(size=10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAllPosts(pageable);
    }

    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody @Valid PostCreateRequest request) {
        postService.makePost(request);
        return new ResponseEntity<>("생성 성공", HttpStatus.OK);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<String> editPost(@PathVariable("id") long id, @RequestBody @Valid PostUpdateRequest request) {
        postService.editPost(id, request);
        return new ResponseEntity<>("수정 성공", HttpStatus.OK);
    }
}
