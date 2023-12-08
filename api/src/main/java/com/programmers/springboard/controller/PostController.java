package com.programmers.springboard.controller;

import com.programmers.springboard.request.PostCreateRequest;
import com.programmers.springboard.request.PostUpdateRequest;
import com.programmers.springboard.response.ApiResponse;
import com.programmers.springboard.response.PostResponse;
import com.programmers.springboard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/{id}")
	public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
		PostResponse postResponse = postService.getPostById(id);
		return ApiResponse.createSuccess(postResponse);
	}

	@GetMapping
	public ApiResponse<List<PostResponse>> getPosts(
		@RequestParam(required = false, value = "page", defaultValue = "1") Integer page) {
		List<PostResponse> postResponseList = postService.getPosts(page);
		return ApiResponse.createSuccess(postResponseList);
	}

	@PostMapping
	public ApiResponse<PostResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
		PostResponse post = postService.createPost(postCreateRequest);
		return ApiResponse.createSuccess(post);
	}

	@PutMapping("/{id}")
	public ApiResponse<PostResponse> updatePost(@PathVariable Long id,
		@Valid @RequestBody PostUpdateRequest postUpdateRequest) {
		PostResponse post = postService.updatePost(id, postUpdateRequest);
		return ApiResponse.createSuccess(post);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<?> deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return ApiResponse.createSuccessWithNoContent();
	}
}
