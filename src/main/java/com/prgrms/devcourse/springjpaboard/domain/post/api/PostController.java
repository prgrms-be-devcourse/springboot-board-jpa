package com.prgrms.devcourse.springjpaboard.domain.post.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.facade.PostFacade;

import jakarta.validation.Valid;
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
	public ResponseEntity<Void> create(@Valid @RequestBody PostSaveDto postRequestDto) {

		postFacade.create(postRequestDto);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> edit(@PathVariable(name = "id") Long id,@Valid @RequestBody PostUpdateDto postUpdateDto) {
		postFacade.update(id, postUpdateDto);

		return ResponseEntity.ok().build();
	}
}
