package com.ray.springbootboard.controller;

import com.ray.springbootboard.controller.dto.PageResponse;
import com.ray.springbootboard.controller.dto.PostResponse;
import com.ray.springbootboard.controller.dto.PostSaveRequest;
import com.ray.springbootboard.controller.dto.PostUpdateRequest;
import com.ray.springbootboard.domain.Post;
import com.ray.springbootboard.domain.User;
import com.ray.springbootboard.service.PostService;
import com.ray.springbootboard.service.UserService;
import com.ray.springbootboard.service.vo.PostUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> savePost(@RequestBody PostSaveRequest request, @RequestParam Long userId) {
        User user = userService.getById(userId);
        Long savedPostId = postService.save(request.toEntity(user));

        return ResponseEntity.created(URI.create("/api/v1/posts/" + savedPostId)).build();
    }

    @GetMapping
    public PageResponse<PostResponse, Post> getAllPosts(Pageable pageable) {
        Page<Post> postPages = postService.findAllWithPage(pageable);

        List<PostResponse> postResponses = postPages.getContent().stream()
                .map(PostResponse::new)
                .toList();

        return new PageResponse<>(postResponses, postPages);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return new PostResponse(postService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@RequestBody PostUpdateRequest request, @PathVariable Long id) {
        PostUpdateInfo postUpdateInfo = new PostUpdateInfo(id, request.title(), request.content());
        Long updatedPostId = postService.update(postUpdateInfo);

        return ResponseEntity.noContent()
                .location(URI.create("/api/v1/posts/" + updatedPostId))
                .build();
    }
}
