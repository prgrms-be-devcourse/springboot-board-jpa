package org.prgrms.board.domain.post.api;

import org.prgrms.board.domain.post.Service.PostService;
import org.prgrms.board.domain.post.request.PostCreateRequest;
import org.prgrms.board.domain.post.request.PostUpdateRequest;
import org.prgrms.board.domain.post.response.PostSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<Page<PostSearchResponse>> getAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostSearchResponse> getOne(@PathVariable long postId) {
        return new ResponseEntity<>(postService.findById(postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(
            @Valid @RequestBody PostCreateRequest createRequest, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        postService.save(createRequest, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<HttpStatus> update(
            @PathVariable long postId, @Valid @RequestBody PostUpdateRequest updateRequest) {
        postService.update(postId, updateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable long postId) {
        postService.delete(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
