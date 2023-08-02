package com.jpaboard.domain.post.presentation;

import com.jpaboard.domain.post.application.PostService;
import com.jpaboard.domain.post.dto.request.PostCreateRequest;
import com.jpaboard.domain.post.dto.request.PostSearchRequest;
import com.jpaboard.domain.post.dto.request.PostUpdateRequest;
import com.jpaboard.domain.post.dto.response.PostPageResponse;
import com.jpaboard.domain.post.dto.response.PostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> postCreate(@RequestBody @Valid PostCreateRequest request) {
        Long postId = postService.createPost(request);
        return ResponseEntity.created(URI.create("/api/posts/" + postId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> postDetail(@PathVariable Long id) {
        PostResponse response = postService.findPostById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> postFindAll(@PageableDefault Pageable pageable) {
        PostPageResponse response = postService.findPosts(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<PostPageResponse> findByCondition(
            @ModelAttribute PostSearchRequest request,
            @PageableDefault Pageable pageable
    ){
        PostPageResponse response = postService.findPostsByCondition(request, pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> postUpdate(@PathVariable Long id, @RequestBody @Valid PostUpdateRequest request) {
        postService.updatePost(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> postDelete(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

}
