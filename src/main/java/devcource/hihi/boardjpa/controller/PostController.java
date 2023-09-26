package devcource.hihi.boardjpa.controller;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.dto.CursorResult;
import devcource.hihi.boardjpa.dto.post.*;
import devcource.hihi.boardjpa.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final int DEFAULT_SIZE = 10;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public CursorResult<Post> getPostsByCursor(@RequestParam(value = "cursor", required = false) Long cursorId,
                                               @RequestParam(value = "size") Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        return this.postService.get(cursorId, PageRequest.of(0, size));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ResponsePostDto> createPost(@PathVariable Long userId,@Valid @RequestBody CreateRequestDto postDto) {
        return ResponseEntity.ok(postService.createPost(userId, postDto));
    }

    @GetMapping("/{postIid}")
    public ResponseEntity<ResponsePostDto> findByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPost(postId));
    }

    @PutMapping("/{postIid}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable Long postId, UpdateRequestDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto));
    }

    @DeleteMapping("/{postIid}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }


}
