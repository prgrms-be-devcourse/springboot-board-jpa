package com.prgrms.devcourse.springjpaboard.domain.post.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping("/api/v1/posts")
	public ResponseEntity<List<Post>> findAll() {

		List<Post> postList = postService.findAll();

		return ResponseEntity.ok(postList);
	}

	@GetMapping("/api/v1/posts/{id}")
	public ResponseEntity<Post> findById(@PathVariable(name = "id") Long id) {

		Post post = postService.findById(id);

		return ResponseEntity.ok(post);
	}

	@PostMapping("/api/v1/posts")
	public ResponseEntity<Void> create(@RequestBody Post post) {
		postService.create(post);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/api/v1/posts/{id}")
	public ResponseEntity<Void> edit(@PathVariable(name = "id") Long id,@RequestBody Post post) {
		postService.update(id, post);

		return ResponseEntity.ok().build();
	}
}
