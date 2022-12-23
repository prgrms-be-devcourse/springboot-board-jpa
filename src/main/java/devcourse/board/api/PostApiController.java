package devcourse.board.api;

import devcourse.board.domain.post.PostService;
import devcourse.board.domain.post.model.PostRequest;
import devcourse.board.domain.post.model.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok()
                .body(new ApiResponse<>("post-info", postService.findOneAsDto(postId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok()
                .body(new ApiResponse<>("content", postService.findWithPaging(page, size)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(
            @RequestBody PostRequest.CreationDto creationDto
    ) {
        return ResponseEntity.ok()
                .body(new ApiResponse<>("post-id", postService.createPost(creationDto)));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequest.UpdateDto updateDto
    ) {
        return ResponseEntity.ok()
                .body(new ApiResponse<>("post-info", postService.updatePost(postId, updateDto)));
    }
}
