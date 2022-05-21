package org.programmers.springbootboardjpa.web.controller.api.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.service.post.PostService;
import org.programmers.springbootboardjpa.web.dto.LongIdResponse;
import org.programmers.springbootboardjpa.web.dto.post.PostCreateFormV1;
import org.programmers.springbootboardjpa.web.dto.post.PostDtoV1;
import org.programmers.springbootboardjpa.web.dto.post.PostUpdateFormV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostApiControllerV1 implements PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity<LongIdResponse> createPost(@RequestBody PostCreateFormV1 postCreateForm) {
        Long postId = postService.writePost(postCreateForm);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + postId)).body(new LongIdResponse(postId));
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostDtoV1 showPost(@PathVariable("id") Long postId) {
        return PostDtoV1.from(postService.findPostWithPostId(postId));
    }

    @PatchMapping("/api/v1/posts/{id}")
    public PostDtoV1 editPost(@PathVariable("id") Long postId, @RequestBody PostUpdateFormV1 postUpdateForm) {
        return PostDtoV1.from(postService.editPost(postUpdateForm));
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/posts")
    public Page<PostDtoV1> showPostsPaged(Pageable pageable, @RequestParam(required = false) Long userId) {
        Page<Post> posts = (userId != null) ? postService.findPostByUserWithPage(userId, pageable) : postService.findPostsWithPage(pageable);
        return posts.map(PostDtoV1::from);
    }
}