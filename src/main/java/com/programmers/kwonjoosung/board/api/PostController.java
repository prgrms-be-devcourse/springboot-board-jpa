package com.programmers.kwonjoosung.board.api;

import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.model.dto.PostInfo;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import com.programmers.kwonjoosung.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<List<PostInfo>> getAllPosts() {
        return ResponseEntity.ok(postService.findAllPost());
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<PostInfo>> getPosts(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(postService.findAllPost(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostInfo> getPostByPostId(@PathVariable(name = "id") Long postId) {
        return ResponseEntity.ok(postService.findPostByPostId(postId));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostInfo>> getPostByUserId(@PathVariable(name = "id") Long userId) {
        return ResponseEntity.ok(postService.findPostByUserId(userId));
    }

    @PostMapping("/{id}")
    public ResponseEntity<IdResponse> createPost(@PathVariable(name = "id") Long userId, @Valid @RequestBody CreatePostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(userId, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostInfo> updatePost(@PathVariable(name = "id") Long postId, @RequestBody UpdatePostRequest request) {

        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

}
