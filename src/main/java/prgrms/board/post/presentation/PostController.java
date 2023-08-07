package prgrms.board.post.presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prgrms.board.post.application.PostService;
import prgrms.board.post.application.dto.request.PostSaveRequest;
import prgrms.board.post.application.dto.request.PostUpdateRequest;
import prgrms.board.post.application.dto.response.PostFindResponse;
import prgrms.board.post.application.dto.response.PostSaveResponse;
import prgrms.board.post.application.dto.response.PostUpdateResponse;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostSaveResponse> saveNewPost(
            @RequestBody PostSaveRequest request
    ) {
        var response = postService.savePost(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostUpdateResponse> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        var response = postService.updatePost(id, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostFindResponse>> findAll(
            @PageableDefault(
                    size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        var response = postService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostFindResponse> findByPostId(@PathVariable Long id) {
        var response = postService.findByPostId(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
