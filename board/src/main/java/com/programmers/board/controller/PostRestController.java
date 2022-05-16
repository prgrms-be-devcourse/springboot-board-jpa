package com.programmers.board.controller;

import javax.validation.Valid;

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
}
