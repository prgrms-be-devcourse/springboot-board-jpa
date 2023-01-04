package devcourse.board.web.api.v1;

import devcourse.board.domain.post.PostService;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostResponse;
import devcourse.board.domain.post.model.PostUpdateRequest;
import devcourse.board.domain.post.model.SimplePostResponses;
import devcourse.board.web.model.CreateDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static devcourse.board.web.authentication.AuthenticationUtil.getLoggedInMemberId;

@RestController
@RequestMapping("/api/v1/posts")
public class PostApiV1 {

    private final PostService postService;

    public PostApiV1(PostService postService) {
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
    public ResponseEntity<SimplePostResponses> getPosts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok()
                .body(postService.findWithPaging(page, size));
    }

    @PostMapping
    public ResponseEntity<CreateDataResponse> createPost(
            @RequestBody PostCreationRequest creationRequest
    ) {
        Long postIdentifier = postService.createPost(creationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateDataResponse(postIdentifier));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            HttpServletRequest request,
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest updateRequest
    ) {
        Long loggedInMemberId = getLoggedInMemberId(request);
        return ResponseEntity.ok()
                .body(postService.updatePost(loggedInMemberId, postId, updateRequest));
    }
}
