package jehs.springbootboardjpa.controller;

import jehs.springbootboardjpa.dto.PostCreateRequest;
import jehs.springbootboardjpa.dto.PostResponse;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable(name = "id") Long postId) {
        return postService.getPostResponseById(postId);
    }

    @GetMapping
    public Page<PostResponse> getAllPosts(@PageableDefault Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @PostMapping
    public void createPost(@RequestBody PostCreateRequest postCreateRequest) {
        postService.createPost(postCreateRequest);
    }

    @PatchMapping("/{id}")
    public void updatePost(@PathVariable(name = "id") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        postService.updatePost(postId, postUpdateRequest);
    }
}
