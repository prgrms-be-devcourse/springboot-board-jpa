package devcource.hihi.boardjpa.controller;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.dto.post.CreatePostDto;
import devcource.hihi.boardjpa.dto.post.UpdatePostDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import devcource.hihi.boardjpa.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public ResponseEntity<List<Post>> getPost(@RequestParam Long cursorId, @RequestParam int limit) {
        return ResponseEntity.ok(postService.getPosts(cursorId, limit));
    }

    @PostMapping
    public ResponseEntity<ResponsePostDto> createPost(@Valid @RequestBody CreatePostDto postDto) {
        return ResponseEntity.ok(postService.createDto(postDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostDto> findByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable Long id, UpdatePostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }


}
