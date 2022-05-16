package com.programmers.board.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.board.Service.PostService;

@RequestMapping("/post")
@RestController
public class PostRestController {

	private final PostService postService;

	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	@PostMapping("")
	public PostDto.Response create(@Valid PostDto.Save postDto) {
		return postService.save(postDto);
	}

	@PostMapping("/{post_id}")
	public PostDto.Response update(@Valid PostDto.Update updateDto) {
		return postService.update(updateDto);
	}


	@GetMapping("/{post_id}")
	public PostDto.Response findOne(@PathVariable("post_id") Long postId) {
		return postService.findOne(postId);
	}

	@DeleteMapping("/{post_id}")
	public ResponseEntity<String> softDeleteOne(@PathVariable("post_id")Long postId){
		postService.softDeleteOne(postId);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}

}
