package com.ys.board.domain.post.api;

import com.ys.board.domain.post.PostUpdateRequest;
import com.ys.board.domain.post.service.PostServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostServiceFacade postServiceFacade;

    @PostMapping
    public ResponseEntity<PostCreateResponse> createPost(@RequestBody PostCreateRequest request) {
        PostCreateResponse createResponse = postServiceFacade.createPost(request);

        return new ResponseEntity<>(createResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long postId) {
        PostResponse postResponse = postServiceFacade.findPostById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updateAllPost(
        @PathVariable Long postId,
        @RequestBody PostUpdateRequest request) {

        postServiceFacade.updatePost(postId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PostResponses> findAll(@ModelAttribute PostsRequest request) {
        PostResponses postResponses = postServiceFacade.findAllPostsByIdCursorBased(
            request.getCursorId(), request.getPageSize());

        return ResponseEntity.ok(postResponses);
    }

}
