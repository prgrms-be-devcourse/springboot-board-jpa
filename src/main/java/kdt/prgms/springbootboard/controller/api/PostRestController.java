package kdt.prgms.springbootboard.controller.api;

import java.net.URI;
import javax.validation.Valid;
import kdt.prgms.springbootboard.domain.Post;
import kdt.prgms.springbootboard.dto.PostDetailResponseDto;
import kdt.prgms.springbootboard.dto.PostSaveRequestDto;
import kdt.prgms.springbootboard.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/posts")
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDetailResponseDto> createPost(@RequestBody @Valid PostSaveRequestDto postSaveRequestDto) {
        PostDetailResponseDto responseDto = postService.save(postSaveRequestDto);
        URI location = URI.create("api/v1/posts/" + responseDto.getId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<PostSaveRequestDto>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable long postId) {
        return ResponseEntity.ok(postService.findOne(postId));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> updatePost(@PathVariable long postId,
        @RequestBody @Valid PostSaveRequestDto postSaveRequestDto) {
        return ResponseEntity.ok(postService.update(postId, postSaveRequestDto));
    }
}
