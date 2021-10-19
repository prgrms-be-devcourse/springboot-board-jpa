package kdt.prgms.springbootboard.controller.api;

import javax.validation.Valid;
import kdt.prgms.springbootboard.dto.PostDetailDto;
import kdt.prgms.springbootboard.dto.PostDto;
import kdt.prgms.springbootboard.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/v1")
    public ResponseEntity<Long> create(@RequestBody @Valid PostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.save(postDto));
    }

    @GetMapping("/v1")
    public ResponseEntity<Page<PostDto>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("v1/{postId}")
    public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable long postId) {
        return ResponseEntity.ok(postService.findOne(postId));
    }

    @PostMapping("v1/{postId}")
    public ResponseEntity<Long> updateProfile(@PathVariable long postId,
        @RequestBody @Valid PostDto postDto) {
        return ResponseEntity.ok(postService.update(postId, postDto));
    }


}