package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.DefaultApiResponse;
import prgrms.project.post.service.DefaultPage;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.post.PostService;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<DefaultApiResponse<Long>> uploadPost(@RequestBody @Validated PostDto postDto) {
        return ResponseEntity.ok(DefaultApiResponse.of(postService.uploadPost(postDto)));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<DefaultApiResponse<PostDto>> searchPost(@PathVariable Long postId) {
        return ResponseEntity.ok(DefaultApiResponse.of(postService.searchById(postId)));
    }

    @GetMapping
    public ResponseEntity<DefaultApiResponse<DefaultPage<PostDto>>> searchAllPosts(Pageable pageable) {
        return ResponseEntity.ok(DefaultApiResponse.of(postService.searchAll(pageable)));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<DefaultApiResponse<Long>> updatePost(@PathVariable Long postId, @RequestBody @Validated PostDto postDto) {
        return ResponseEntity.ok(DefaultApiResponse.of(postService.updatePost(postId, postDto)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<DefaultApiResponse<Boolean>> deletePost(@PathVariable Long postId) {
        postService.deleteById(postId);

        return ResponseEntity.ok(DefaultApiResponse.of(true));
    }
}
