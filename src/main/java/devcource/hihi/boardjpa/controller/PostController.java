package devcource.hihi.boardjpa.controller;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.dto.post.*;
import devcource.hihi.boardjpa.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 100;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPostsByCursor(@RequestParam(value = "cursor", defaultValue = "0") Long cursorId,
                                       @RequestParam(value = "size", defaultValue = "2") Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        if (size > MAX_SIZE) size = MAX_SIZE;

        return postService.getPosts(cursorId, size);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ResponsePostDto> createPost(@Valid @RequestBody CreatePostRequestDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @GetMapping("/{postIid}")
    public ResponseEntity<ResponsePostDto> findByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPost(postId));
    }

    @PutMapping("/{postIid}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable Long postId, UpdatePostRequestDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto));
    }

    @DeleteMapping("/{postIid}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }


}
