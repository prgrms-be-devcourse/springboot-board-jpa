package com.prgrms.devcourse.springjpaboard.domain.post.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostUpdateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

	private final PostFacade postFacade;

	@GetMapping
	public ResponseEntity<PostResponseDtos> findAll(@RequestBody PostRequestDto postRequestDto) {

		PostResponseDtos postResponseDtos = postFacade.findAll(postRequestDto);

		return ResponseEntity.ok(postResponseDtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDto> findById(@PathVariable(name = "id") Long id) {

		PostResponseDto postResponseDto = postFacade.findById(id);

		return ResponseEntity.ok(postResponseDto);
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody PostSaveDto postSaveDto) {

		postFacade.create(postSaveDto);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable(name = "id") Long id,
		@Valid @RequestBody PostUpdateDto postUpdateDto) {
		postFacade.update(id, postUpdateDto);

		return ResponseEntity.ok().build();
	}
}
