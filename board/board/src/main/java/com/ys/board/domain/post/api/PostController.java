package com.ys.board.domain.post.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ys.board.domain.post.service.PostServiceFacade;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostCreateResponse> createPost(
        @RequestBody @Valid PostCreateRequest request) {
        PostCreateResponse createResponse = postServiceFacade.createPost(request);

        return new ResponseEntity<>(createResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{postId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> findPostById(@PathVariable Long postId) {
        PostResponse postResponse = postServiceFacade.findPostById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping(value = "/{postId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAllPost(
        @PathVariable Long postId,
        @RequestBody @Valid PostUpdateRequest request) {

        postServiceFacade.updatePost(postId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponses> findAll(@ModelAttribute @Valid PostsRequest request,
        @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
        PostResponses postResponses = postServiceFacade.findAllPostsByIdCursorBased(
            request.getCursorId(), pageable);

        return ResponseEntity.ok(postResponses);
    }

}
