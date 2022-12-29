package devcourse.board.api;

import devcourse.board.api.model.CreateDataResponse;
import devcourse.board.domain.post.PostService;
import devcourse.board.domain.post.model.MultiplePostResponse;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostResponse;
import devcourse.board.domain.post.model.PostUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok()
                .body(postService.getPost(postId));
    }

    @GetMapping
    public ResponseEntity<MultiplePostResponse> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok()
                .body(postService.findWithPaging(page, size));
    }

    @PostMapping
    public ResponseEntity<CreateDataResponse> createPost(
            @RequestBody PostCreationRequest creationDto
    ) {
        Long postIdentifier = postService.createPost(creationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateDataResponse(postIdentifier));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok()
                .body(postService.updatePost(postId, updateRequest));
    }
}
