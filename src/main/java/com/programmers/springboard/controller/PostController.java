package com.programmers.springboard.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.programmers.springboard.request.CreatePostRequest;
import com.programmers.springboard.request.UpdatePostRequest;
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
	public PostResponse getPost(@PathVariable Long id){
		return postService.getPostById(id);
	}

	@GetMapping("/")
	public PostResponse getPosts(@RequestParam(required = false, value = "1", name = "page") Integer page){
		postService.getPosts(page);
		return null;
	}

	@PostMapping
	public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest createPostRequest){
		PostResponse post = postService.createPost(createPostRequest);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{postId}")
			.buildAndExpand(post.postId())
			.toUri();
		return ResponseEntity.created(location).body(post);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @Valid @RequestBody UpdatePostRequest updatePostRequest){
		PostResponse post = postService.updatePost(id, updatePostRequest);
		return ResponseEntity.ok().body(post);
	}
}
