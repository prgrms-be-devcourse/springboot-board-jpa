package devcourse.board.web.api.v2;

import devcourse.board.domain.post.PostService;
import devcourse.board.domain.post.model.PostCreationRequest;
import devcourse.board.domain.post.model.PostResponse;
import devcourse.board.domain.post.model.PostUpdateRequest;
import devcourse.board.domain.post.model.SimplePostResponses;
import devcourse.board.web.authentication.SessionManager;
import devcourse.board.web.model.CreateDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/posts")
public class PostApiV2 {

    private final SessionManager sessionManager;

    private final PostService postService;

    public PostApiV2(SessionManager sessionManager, PostService postService) {
        this.sessionManager = sessionManager;
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
            HttpServletRequest request,
            @RequestBody PostCreationRequest creationDto
    ) {
        Long loginMemberId = sessionManager.getLoginMemberIdentifier(request);
        Long postId = postService.createPost(loginMemberId, creationDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateDataResponse(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            HttpServletRequest request,
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest updateRequest
    ) {
        Long loginMemberIdentifier = sessionManager.getLoginMemberIdentifier(request);
        return ResponseEntity.ok()
                .body(postService.updatePost(loginMemberIdentifier, postId, updateRequest));
    }
}
