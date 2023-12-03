package com.programmers.springboard.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.programmers.springboard.request.PostCreateRequest;
import com.programmers.springboard.request.PostSearchRequest;
import com.programmers.springboard.request.PostUpdateRequest;
import com.programmers.springboard.response.ApiResponse;
import com.programmers.springboard.response.PostResponse;
import com.programmers.springboard.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "게시글 컨트롤러")
@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@Operation(summary = "게시글 조회", description = "특정 ID를 가진 게시글을 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id) {
		PostResponse postResponse = postService.getPostById(id);
		return ResponseEntity.ok(ApiResponse.ok(postResponse));
	}

	@Operation(summary = "게시글 목록 조회", description = "페이징 및 조건을 적용하여 게시글 목록을 조회합니다.")
	@GetMapping
	public ResponseEntity<ApiResponse<Page<PostResponse>>> getPosts(
		@ModelAttribute @Valid PostSearchRequest postSearchRequest, Pageable pageable) {
		Page<PostResponse> postResponses = postService.getPosts(postSearchRequest, pageable);
		return ResponseEntity.ok(ApiResponse.ok(postResponses));
	}

	@Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<PostResponse>> createPost(
		@Valid @RequestBody PostCreateRequest postCreateRequest) {
		PostResponse post = postService.createPost(postCreateRequest);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(post.postId())
			.toUri();
		return ResponseEntity.created(location).body(ApiResponse.created(post));
	}

	@Operation(summary = "게시글 수정", description = "특정 ID를 가진 게시글의 내용을 수정합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long id,
		@Valid @RequestBody PostUpdateRequest postUpdateRequest) {
		PostResponse post = postService.updatePost(id, postUpdateRequest);
		return ResponseEntity.ok(ApiResponse.ok(post));
	}

	@Operation(summary = "게시글 삭제", description = "하나 이상의 게시글을 삭제합니다.")
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deletePosts(@RequestParam List<Long> ids) {
		postService.deletePosts(ids);
		return ResponseEntity.ok(ApiResponse.noContent());
	}
}
