package com.prgrms.devcourse.springjpaboard.domain.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.api.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	public PostResponseDto findById(Long id) {
		return postRepository.findById(id).orElseThrow().toPostResponseDto();
	}

	public void create(PostRequestDto postRequestDto) {
		postRepository.save(postRequestDto.toPost());
	}

	public void update(Long id, PostUpdateDto postUpdateDto) {

		Post savedPost = postRepository.findById(id).orElseThrow();

		savedPost.updatePost(postUpdateDto);

	}
}
