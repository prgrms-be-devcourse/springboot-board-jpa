package com.programmers.kwonjoosung.board.api;

import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.model.dto.PostResponse;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import com.programmers.kwonjoosung.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping()
    public ResponseEntity<List<PostResponse>>getAllPosts() {
        return ResponseEntity.ok(postService.findAllPost());
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<PostResponse>>getPosts(@PathVariable(name = "page") Integer startPage, @RequestParam(required = false) Integer size) {
        if(size == null || size == 0) {
            size = 10;
        }
        PageRequest pageRequest = PageRequest.of(startPage, size);
        return ResponseEntity.ok(postService.findAllPost(pageRequest));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostResponse>> getPostByUserId(@PathVariable(name = "id") Long userId) {
        return ResponseEntity.ok(postService.findPostByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostByPostId(@PathVariable(name = "id") Long postId) {
        return ResponseEntity.ok(postService.findPostByPostId(postId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<IdResponse> createPost(@PathVariable(name = "id") Long userId, @Valid @RequestBody CreatePostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId,request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable(name = "id") Long postId, @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok().build();
    }

}
