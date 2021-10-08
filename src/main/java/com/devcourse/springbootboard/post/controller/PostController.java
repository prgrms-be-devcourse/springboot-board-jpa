package com.devcourse.springbootboard.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.springbootboard.post.dto.PostResponse;
import com.devcourse.springbootboard.post.dto.PostUpdateRequest;
import com.devcourse.springbootboard.post.dto.PostWriteRequest;
import com.devcourse.springbootboard.post.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public ResponseEntity<Page<PostResponse>> getAll(final Pageable pageable) {
		return ResponseEntity.ok(postService.findPosts(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable final Long id) {
		return ResponseEntity.ok(postService.findPost(id));
	}

	@PostMapping
	public ResponseEntity<PostResponse> addPost(@RequestBody final PostWriteRequest postWriteRequest) {
		return ResponseEntity.ok(postService.savePost(postWriteRequest));
	}

	@PutMapping
	public ResponseEntity<PostResponse> modifyPost(@RequestBody final PostUpdateRequest postUpdateRequest) {
		return ResponseEntity.ok(postService.updatePost(postUpdateRequest));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> removePost(@PathVariable final Long id) {
		return ResponseEntity.ok(postService.deletePost(id));
	}
}
