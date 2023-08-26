package devcource.hihi.boardjpa.controller;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.dto.post.CreateRequestDto;
import devcource.hihi.boardjpa.dto.post.SearchResponseDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import devcource.hihi.boardjpa.dto.post.UpdateRequestDto;
import devcource.hihi.boardjpa.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public SearchResponseDto<Post> getPostsByCursor(@RequestParam(value = "cursor", required = false) Long cursor,
                                                    @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return postService.getPostsByCursor(cursor, limit);
    }

    @PostMapping
    public ResponseEntity<ResponsePostDto> createPost(@Valid @RequestBody CreateRequestDto postDto) {
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostDto> findByPostId(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findPost(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable Long id, UpdateRequestDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }


}
