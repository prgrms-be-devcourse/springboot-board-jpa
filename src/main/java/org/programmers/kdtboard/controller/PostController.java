package org.programmers.kdtboard.controller;

import java.util.List;

import javax.validation.Valid;

import org.programmers.kdtboard.controller.response.ApiResponse;
import org.programmers.kdtboard.dto.PostDto.PostCreateRequest;
import org.programmers.kdtboard.dto.PostDto.PostResponse;
import org.programmers.kdtboard.dto.PostDto.PostUpdateRequest;
import org.programmers.kdtboard.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("/api/v1/post")
	public ApiResponse<PostResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest) {
		var postResponse = this.postService.create(postCreateRequest);

		return ApiResponse.ok(postResponse);
	}

	@GetMapping("/api/v1/post/{id}")
	public ApiResponse<PostResponse> findById(@PathVariable Long id) {
		var foundPost = this.postService.findById(id);

		return ApiResponse.ok(foundPost);
	}

	@GetMapping("/api/v1/posts")
	public ApiResponse<List<PostResponse>> findAll(Pageable pageable) {
		var posts = this.postService.findAll(pageable).getContent();

		return ApiResponse.ok(posts);
	}

	@PutMapping("/api/v1/post/{id}")
	public ApiResponse<PostResponse> update(@PathVariable Long id, @RequestBody PostUpdateRequest postUpdateRequest) {
		var updatePost = this.postService.update(id, postUpdateRequest);

		return ApiResponse.ok(updatePost);
	}
}
