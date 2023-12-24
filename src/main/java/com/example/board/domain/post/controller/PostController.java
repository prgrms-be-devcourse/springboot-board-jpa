package com.example.board.domain.post.controller;

import com.example.board.domain.common.dto.PageResponseDto;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.service.PostService;
import com.example.board.global.security.jwt.JwtAuthentication;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시물 작성", description = "로그인한 회원만 게시물을 작성할 수 있다.")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@AuthenticationPrincipal JwtAuthentication authentication, @Valid @RequestBody PostCreateRequest request) {
        PostResponse post = postService.createPost(authentication.username(), request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.id())
                .toUri();
        return ResponseEntity.created(location).body(post);
    }

    @Operation(summary = "게시물 상세 조회", description = "게시물 아이디로 게시물을 조회할 수 있다.")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.findPostById(id);
        postService.updatePostViewById(id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시물 조회 페이지", description = "이메일, 제목으로 게시물 페이지를 조회할 수 있다.")
    @GetMapping
    public ResponseEntity<PageResponseDto<PostResponse>> getPostsByCondition(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(value = "email", defaultValue = "", required = false) String email,
            @RequestParam(value = "title", defaultValue = "", required = false) String title
    ) {
        PostPageCondition condition = new PostPageCondition(page, size, email, title);
        Page<PostResponse> posts = postService.findPostsByCondition(condition);
        return ResponseEntity.ok(PageResponseDto.of(posts));
    }

    @Operation(summary = "게시물 수정", description = "게시물 작성자만 해당 게시물의 제목, 내용을 수정할 수 있다.")
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @AuthenticationPrincipal JwtAuthentication authentication,
            @Valid @RequestBody PostUpdateRequest request) {
        PostResponse post = postService.updatePost(id, authentication.username(), request);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시물 단건 삭제", description = "게시물 아이디로 자신이 작성한 게시물을 삭제할 수 있다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id, @AuthenticationPrincipal JwtAuthentication authentication) {
        postService.deletePostById(id, authentication.username());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시물 전체 삭제", description = "게시물 전체를 삭제할 수 있다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllPosts() {
        postService.deleteAllPosts();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원의 게시물 전체 삭제", description = "회원은 자신이 작성한 게시물 전체를 삭제할 수 있다.")
    @DeleteMapping("/writer")
    public ResponseEntity<Void> deleteAllPostsByWriter(@AuthenticationPrincipal JwtAuthentication authentication) {
        postService.deleteAllPostsByWriter(authentication.username());
        return ResponseEntity.noContent().build();
    }
}
