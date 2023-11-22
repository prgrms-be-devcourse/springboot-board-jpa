package kdt.jpa.board.post.controller;

import kdt.jpa.board.post.service.PostService;
import kdt.jpa.board.post.service.dto.request.CreatePostRequest;
import kdt.jpa.board.post.service.dto.request.EditPostRequest;
import kdt.jpa.board.post.service.dto.response.PostListResponse;
import kdt.jpa.board.post.service.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> savePost(@RequestBody CreatePostRequest request) {
        Long postId = postService.createPost(request);

        return ResponseEntity.ok(postId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse postResponse = postService.getById(id);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping
    public ResponseEntity<PostListResponse> getPosts(@PageableDefault Pageable pageable) {
        PostListResponse postListResponse = postService.getPosts(pageable);

        return ResponseEntity.ok(postListResponse);
    }

    @PatchMapping
    public ResponseEntity<Boolean> updatePost(@RequestBody EditPostRequest request) {
        boolean result = postService.editPost(request);

        return ResponseEntity.ok(result);
    }
}
