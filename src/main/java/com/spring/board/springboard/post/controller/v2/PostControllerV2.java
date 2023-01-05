package com.spring.board.springboard.post.controller.v2;

import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.PostDetailResponseDto;
import com.spring.board.springboard.post.domain.dto.PostSummaryResponseDto;
import com.spring.board.springboard.post.service.PostService;
import com.spring.board.springboard.user.controller.authenticate.session.Session;
import com.spring.board.springboard.user.controller.authenticate.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v2/posts")
public class PostControllerV2 {

    private final PostService postService;
    private final SessionManager sessionManager;

    public PostControllerV2(PostService postService, SessionManager sessionManager) {
        this.postService = postService;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<PostSummaryResponseDto>> getAllPosts(Pageable pageable) {
        final List<PostSummaryResponseDto> postList = postService.getAll(pageable);
        return ResponseEntity.ok(postList);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto, HttpServletRequest request) {
        final Session session = sessionManager.findSession(request);

        final PostDetailResponseDto newPostDto = postService.createPost(postCreateRequestDto, session.email());

        final URI uriComponents = UriComponentsBuilder.fromUriString("/posts/{postId}")
                .buildAndExpand(
                        newPostDto.postId())
                .toUri();

        return ResponseEntity.created(uriComponents)
                .build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPost(@PathVariable Integer postId) {
        final PostDetailResponseDto postResponseDTO = postService.getOne(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> updatePost(@PathVariable Integer postId,
                                                            @Valid @RequestBody PostCreateRequestDto postCreateRequestDTO,
                                                            HttpServletRequest request) {
        final Session session = sessionManager.findSession(request);

        final PostDetailResponseDto updatedPostResponseDto = postService.update(postId, postCreateRequestDTO, session.email());
        return ResponseEntity.ok(updatedPostResponseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId, HttpServletRequest request) {
        final Session session = sessionManager.findSession(request);
        postService.deleteOne(postId, session.email());

        return ResponseEntity.noContent()
                .build();
    }
}
