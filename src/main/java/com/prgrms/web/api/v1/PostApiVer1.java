package com.prgrms.web.api.v1;

import static com.prgrms.dto.PostDto.*;
import static com.prgrms.web.auth.CookieUtil.*;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.domain.post.PostService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/posts")
public class PostApiVer1 {

	private final PostService service;

	public PostApiVer1(@Validated PostService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getPostById(@PathVariable Long id) {

		return ResponseEntity.ok(service.findPostById(id));
	}

	@PostMapping
	public ResponseEntity<Void> registerPost(@RequestBody Request postDto,
		HttpServletRequest request) {

		long userId = Long.parseLong(getCookie(request).getValue());
		Response responsePostDto = service.insertPost(userId, postDto);
		URI redirectPath = URI.create("api/v1/posts/" + responsePostDto.getPostId());

		return ResponseEntity.created(redirectPath)
			.build();
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<Response> modifyPost(@PathVariable Long postId,
		@RequestBody Update postDto,
		HttpServletRequest request) {

		long userId = Long.parseLong(getCookie(request).getValue());
		Response response = service.updatePost(postId, userId, postDto);

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<ResponsePostDtos> getPostsByPage(Pageable pageable) {

		return ResponseEntity.ok(service.getPostsByPage(pageable));
	}

}
