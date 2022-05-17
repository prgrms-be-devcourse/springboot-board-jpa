package com.kdt.springbootboardjpa.controller;

import com.kdt.springbootboardjpa.PostService;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class RestPostController {

    private final PostService postService;

    public RestPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{id}")
    public PostDTO getPost(@PathVariable("id") long id) {
        return postService.findPost(id);
    }

    @GetMapping("/posts")
    public List<PostDTO> getPostList() {
        return postService.findAllPosts();
    }

    @PostMapping("/posts")
    public ResponseEntity<String> createPost(@RequestBody PostCreateRequest request) {
        postService.makePost(request);
        return new ResponseEntity<>("생성 성공", HttpStatus.OK);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<String> editPost(@PathVariable("id") long id, @RequestBody PostDTO postDTO) {
        postService.editPost(id, postDTO);
        return new ResponseEntity<>("수정 성공", HttpStatus.OK);
    }
}
