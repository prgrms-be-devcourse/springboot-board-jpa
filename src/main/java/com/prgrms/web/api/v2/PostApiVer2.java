package com.prgrms.web.api.v2;

import static com.prgrms.dto.PostDto.*;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.domain.post.PostService;
import com.prgrms.web.auth.SessionManager;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v2/posts")
public class PostApiVer2 {

	private final PostService service;
	private final SessionManager sessionManager;

	public PostApiVer2(PostService service, SessionManager sessionManager) {
		this.service = service;
		this.sessionManager = sessionManager;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getPostById(@PathVariable Long id) {

		return ResponseEntity.ok(service.findPostById(id));
	}

	@PostMapping
	public ResponseEntity<Void> registerPost(@RequestBody Request postDto,
		HttpServletRequest request) {

		long userId = sessionManager.getSession(request);
		Response savedPost = service.insertPost(userId, postDto);
		URI getPostByIdPath = URI.create("api/v2/posts/" + savedPost.getPostId());

		return ResponseEntity.created(getPostByIdPath)
			.build();
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<Response> editPost(@PathVariable Long postId,
		@RequestBody Update postDto,
		HttpServletRequest request) {

		long userId = sessionManager.getSession(request);
		Response updatedPost = service.updatePost(postId, userId, postDto);

		return ResponseEntity.ok(updatedPost);
	}

	@GetMapping
	public ResponseEntity<ResponsePostDtos> getPostsByPage(Pageable pageable) {

		return ResponseEntity.ok(service.getPostsByPage(pageable));
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId, HttpServletRequest request) {

		long userId = sessionManager.getSession(request);
		service.deletePost(postId, userId);

		return ResponseEntity.noContent()
			.build();
	}

}
