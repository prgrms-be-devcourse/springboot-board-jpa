package com.seungwon.board.post.presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seungwon.board.post.application.PostService;
import com.seungwon.board.post.application.dto.PostRequestDto;
import com.seungwon.board.post.application.dto.PostResponseDto;
import com.seungwon.board.post.application.dto.PostSaveRequestDto;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping()
	ResponseEntity<Page<PostResponseDto>> findAll(
			@PageableDefault(sort = "id", size = 5, direction = Sort.Direction.ASC)
			Pageable pageable) {

		Page<PostResponseDto> response = postService.findAll(pageable);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	ResponseEntity<PostResponseDto> findBy(@PathVariable("id") Long id) {

		PostResponseDto response = postService.findBy(id);

		return ResponseEntity.ok(response);
	}

	@PostMapping()
	ResponseEntity<PostSaveRequestDto> create(@RequestBody PostRequestDto postRequestDto) {

		PostSaveRequestDto postSaveRequestDto = postService.create(postRequestDto);

		return ResponseEntity.ok(postSaveRequestDto);
	}

	@PatchMapping("/{id}")
	ResponseEntity<PostSaveRequestDto> update(@PathVariable("id") Long id, @RequestBody PostRequestDto postRequestDto) {

		PostSaveRequestDto postSaveRequestDto = postService.update(id, postRequestDto);

		return ResponseEntity.ok(postSaveRequestDto);
	}
}
