package com.prgrms.board.post.controller;

import com.prgrms.board.post.controller.dto.*;
import com.prgrms.board.post.service.PostService;
import com.prgrms.board.post.service.dto.PostDetailedParam;
import com.prgrms.board.post.service.dto.PostDetailedResult;
import com.prgrms.board.post.service.dto.PostDetailedResults;
import com.prgrms.board.post.service.dto.PostShortResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.prgrms.board.post.controller.PostController.POST_REST_URI;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = POST_REST_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class PostController {

    public static final String POST_REST_URI = "api/posts";

    private final PostService service;
    private final PostControllerConverter converter;

    public PostController(PostService service, PostControllerConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping
    public ResponseEntity<PostShortResponse> create(@RequestBody @Validated PostDetailedRequest request) {
        PostDetailedParam param = converter.toPostDetailedParam(request);
        PostShortResult result = service.save(param);

        PostShortResponse response = converter.toPostShortResponse(result);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PostDetailedResponse> findById(@PathVariable Long id) {
        PostDetailedResult result = service.findById(id);

        PostDetailedResponse response = converter.toPostDetailedResponse(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<PostDetailedResponses> findAllWithPagination(Pageable pageable) {
        PostDetailedResults retrievedPosts = service.findAllWithPagination(pageable);
        PostDetailedResponses convertedPosts = converter.toPostDetailedResponses(retrievedPosts);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(convertedPosts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostShortResponse> update(@PathVariable Long id,
                                                    @RequestBody @Validated PostDetailedRequest request) {
        PostDetailedParam param = converter.toPostDetailedParam(request);
        PostShortResult result = service.update(id, param);

        PostShortResponse response = converter.toPostShortResponse(result);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
