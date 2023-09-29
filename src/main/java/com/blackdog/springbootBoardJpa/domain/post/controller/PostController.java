package com.blackdog.springbootBoardJpa.domain.post.controller;

import com.blackdog.springbootBoardJpa.domain.post.controller.converter.PostControllerConverter;
import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostCreateDto;
import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostUpdateDto;
import com.blackdog.springbootBoardJpa.domain.post.service.PostService;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponse;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponses;
import com.blackdog.springbootBoardJpa.global.response.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.blackdog.springbootBoardJpa.global.response.SuccessCode.POST_DELETE_SUCCESS;

@RestController
@RequestMapping(path = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final PostService postService;
    private final PostControllerConverter controllerConverter;

    public PostController(
            final PostService postService,
            final PostControllerConverter controllerConverter
    ) {
        this.postService = postService;
        this.controllerConverter = controllerConverter;
    }

    /**
     * [게시글 저장 API]
     *
     * @param userId
     * @param createDto
     * @return ResponseEntity<PostResponse>
     */
    @PostMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> savePost(@PathVariable Long userId, @Valid @RequestBody PostCreateDto createDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.savePost(
                        userId,
                        controllerConverter.toCreateRequest(createDto)));
    }

    /**
     * [게시글 수정 API]
     *
     * @param postId
     * @param userId
     * @param updateDto
     * @return ResponseEntity<PostResponse>
     */
    @PatchMapping(path = "/{postId}/user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @PathVariable Long userId, @Valid @RequestBody PostUpdateDto updateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.updatePost(
                        postId,
                        userId,
                        controllerConverter.toUpdateRequest(updateDto)));
    }

    /**
     * [게시글 삭제 API]
     *
     * @param postId
     * @param userId
     * @return ResponseEntity<SuccessResponse>
     */
    @DeleteMapping(path = "/{postId}/user/{userId}")
    public ResponseEntity<SuccessResponse> deletePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.deletePostById(userId, postId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(POST_DELETE_SUCCESS));
    }

    /**
     * [게시글 상세 조회 API]
     *
     * @param postId
     * @return ResponseEntity<PostResponse>
     */
    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findPostById(postId));
    }

    /**
     * [게시글 전체 조회 API]
     *
     * @param userId
     * @param pageable
     * @return ResponseEntity<PostResponses>
     */
    @GetMapping
    public ResponseEntity<PostResponses> getPosts(@RequestParam(defaultValue = "-1") Long userId, Pageable pageable) {
        PostResponses postResponses = (userId == -1)
                ? postService.findAllPosts(pageable)
                : postService.findPostsByUserId(userId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postResponses);
    }

}
