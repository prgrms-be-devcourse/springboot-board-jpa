package com.programmers.jpaboard.domain.post.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.jpaboard.common.exception.PostNotFoundException;
import com.programmers.jpaboard.common.exception.UserNotFoundException;
import com.programmers.jpaboard.domain.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDtoList;
import com.programmers.jpaboard.domain.post.dto.PostUpdateRequestDto;
import com.programmers.jpaboard.domain.post.entity.Post;
import com.programmers.jpaboard.domain.post.repository.PostRepository;
import com.programmers.jpaboard.domain.post.util.PostConverter;
import com.programmers.jpaboard.domain.user.entity.User;
import com.programmers.jpaboard.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Transactional
	public PostResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
		User user = userRepository.findById(postCreateRequestDto.getUserId())
			.orElseThrow(() -> new UserNotFoundException());

		Post post = PostConverter.toPost(postCreateRequestDto, user);
		Post savedPost = postRepository.save(post);
		PostResponseDto postResponseDto = PostConverter.toPostResponseDto(savedPost);

		return postResponseDto;
	}

	@Transactional(readOnly = true)
	public PostResponseDto getPostById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());
		PostResponseDto postResponseDto = PostConverter.toPostResponseDto(post);

		return postResponseDto;
	}

	@Transactional(readOnly = true)
	public PostResponseDtoList getPosts(Pageable pageable) {
		List<PostResponseDto> postResponseDtoList
			= postRepository.findAll(pageable)
			.stream()
			.map(PostConverter::toPostResponseDto)
			.toList();

		return new PostResponseDtoList(postResponseDtoList);
	}

	@Transactional
	public PostResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException());
		post.changePost(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());
		PostResponseDto updatedPostDto = PostConverter.toPostResponseDto(post);

		return updatedPostDto;
	}
}
