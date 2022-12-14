package com.spring.board.springboard.post.controller;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> postList = postService.getAll();
        return ResponseEntity.ok(postList);
    }
}
