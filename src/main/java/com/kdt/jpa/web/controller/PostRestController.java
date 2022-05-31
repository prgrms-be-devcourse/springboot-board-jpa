package com.kdt.jpa.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;
import com.kdt.jpa.domain.post.service.PostService;

@RequestMapping("/api/v1/posts")
@RestController
public class PostRestController {

	private final PostService postService;

	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public ResponseEntity<Page<PostResponse>> getAll(Pageable pageable) {
		return ResponseEntity.ok(postService.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(postService.findById(id));
	}

	@PostMapping
	public ResponseEntity<PostResponse.WritePostResponse> write(@RequestBody PostRequest.WritePostRequest request) {
		return ResponseEntity.ok(postService.write(request));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PostResponse.UpdatePostResponse> update(@PathVariable Long id, @RequestBody PostRequest.UpdatePostRequest request) {
		return ResponseEntity.ok(postService.update(id, request));
	}
}
