package com.prgrms.devcourse.springjpaboard.domain.post.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

	private final PostService postService;

	@GetMapping
	public ResponseEntity<PostResponseDtos> findAll() {

		PostResponseDtos postResponseDtos = postService.findAll();

		return ResponseEntity.ok(postResponseDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDto> findById(@PathVariable(name = "id") Long id) {

		PostResponseDto postResponseDto = postService.findById(id);

		return ResponseEntity.ok(postResponseDto);
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody PostRequestDto postRequestDto) {
		postService.create(postRequestDto);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> edit(@PathVariable(name = "id") Long id,@Valid @RequestBody PostUpdateDto postUpdateDto) {
		postService.update(id, postUpdateDto);

		return ResponseEntity.ok().build();
	}
}
