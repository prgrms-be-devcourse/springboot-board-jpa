package com.programmers.springboard.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.programmers.springboard.request.PostCreateRequest;
import com.programmers.springboard.request.PostUpdateRequest;
import com.programmers.springboard.response.ApiResponse;
import com.programmers.springboard.response.PostResponse;
import com.programmers.springboard.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id) {
		PostResponse postResponse = postService.getPostById(id);
		return ResponseEntity.ok(ApiResponse.ok(postResponse));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts(
		@RequestParam(required = false, value = "page", defaultValue = "1") Integer page) { // todo : Pageable 적용
		List<PostResponse> postResponses = postService.getPosts(page);
		return ResponseEntity.ok(ApiResponse.ok(postResponses));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
		PostResponse post = postService.createPost(postCreateRequest);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(post.postId())
			.toUri();
		return ResponseEntity.created(location).body(ApiResponse.created(post));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long id,
		@Valid @RequestBody PostUpdateRequest postUpdateRequest) {
		PostResponse post = postService.updatePost(id, postUpdateRequest);
		return ResponseEntity.ok(ApiResponse.ok(post));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deletePosts(@RequestParam List<Long> ids) {
		postService.deletePosts(ids);
		ApiResponse<Void> response = ApiResponse.noContent();
		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	}
}
