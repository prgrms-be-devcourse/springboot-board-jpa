package org.prgms.boardservice.domain.post;

import org.prgms.boardservice.domain.post.dto.PageResponse;
import org.prgms.boardservice.domain.post.dto.PostCreateRequest;
import org.prgms.boardservice.domain.post.dto.PostResponse;
import org.prgms.boardservice.domain.post.dto.PostUpdateRequest;
import org.prgms.boardservice.domain.post.vo.PostUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<String> noSuchElementExceptionHandle(NoSuchElementException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostCreateRequest postCreateRequest) {
        Long postId = postService.create(postCreateRequest.toEntity());

        return ResponseEntity.created(URI.create("/api/v1/posts/" + postId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getOne(@PathVariable Long id) throws NoSuchElementException {
        PostResponse postResponse = new PostResponse(postService.getById(id));

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PostResponse>> getPage(Pageable pageable) throws NoSuchElementException {
        Page<Post> page = postService.getByPage(pageable);
        Page<PostResponse> postResponseDtoPage = page.map(PostResponse::new);

        return ResponseEntity.ok(new PageResponse<>(postResponseDtoPage));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody PostUpdateRequest postUpdateRequest, @PathVariable Long id) {
        Long postId = postService.update(new PostUpdate(id, postUpdateRequest.title(), postUpdateRequest.content()));

        return ResponseEntity.noContent()
                .location(URI.create("/api/v1/posts/" + postId))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deleteById(id);

        return ResponseEntity.noContent()
                .build();
    }
}
