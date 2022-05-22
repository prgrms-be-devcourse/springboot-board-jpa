package me.prgms.board.post.contoller;

import me.prgms.board.post.dto.CreatePostDto;
import me.prgms.board.post.dto.ResponsePostDto;
import me.prgms.board.post.dto.UpdatePostDto;
import me.prgms.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> exceptionHandle(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<ResponsePostDto>> getPagePosts(Pageable pageable) {
        return ResponseEntity.ok(postService.findPosts(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CreatePostDto postDto) {
        return ResponseEntity.ok(postService.create(postDto));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Long> update(@PathVariable Long postId, @RequestBody UpdatePostDto postDto) {
        return ResponseEntity.ok(postService.update(postId, postDto));
    }

}
