package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.IdResponse;
import prgrms.project.post.service.DefaultPage;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.post.PostService;

import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<IdResponse> uploadPost(@RequestBody @Validated PostDto postDto) {
        return ResponseEntity.ok(IdResponse.of(postService.uploadPost(postDto)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> searchPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.searchById(postId));
    }

    @GetMapping
    public ResponseEntity<DefaultPage<PostDto>> searchAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.searchAll(pageable));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<IdResponse> updatePost(@PathVariable Long postId, @RequestBody @Validated PostDto postDto) {
        return ResponseEntity.ok(IdResponse.of(postService.updatePost(postId, postDto)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Map<String, Boolean>> deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);

        return ResponseEntity.ok(singletonMap("deleted", true));
    }
}
