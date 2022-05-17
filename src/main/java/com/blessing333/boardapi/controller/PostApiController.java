package com.blessing333.boardapi.controller;

import com.blessing333.boardapi.controller.dto.PostInformation;

import com.blessing333.boardapi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostInformation> getPost(@PathVariable Long id){
        PostInformation postInformation = postService.loadPostById(id);
        return ResponseEntity.ok(postInformation);
    }
}
