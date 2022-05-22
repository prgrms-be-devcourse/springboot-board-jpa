package org.programmers.kdtboard.service;

import static java.text.MessageFormat.*;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.converter.PostConverter;
import org.programmers.kdtboard.converter.UserConverter;
import org.programmers.kdtboard.domain.post.Post;
import org.programmers.kdtboard.domain.post.PostRepository;
import org.programmers.kdtboard.dto.PostDto.PostCreateRequestDto;
import org.programmers.kdtboard.dto.PostDto.PostResponseDto;
import org.programmers.kdtboard.dto.PostDto.PostUpdateRequestDto;
import org.programmers.kdtboard.exception.NotFoundEntityByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;
	private final PostConverter postConverter;
	private final UserConverter userConverter;

	public PostService(PostRepository postRepository, UserService userService,
		PostConverter postConverter, UserConverter userConverter) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.postConverter = postConverter;
		this.userConverter = userConverter;
	}

	@Transactional
	public PostResponseDto create(PostCreateRequestDto postCreateRequest) {
		var post = Post.builder().title(postCreateRequest.title())
			.content(postCreateRequest.content())
			.build();
		var userResponseDto = this.userService.findById(postCreateRequest.userId());
		post.setUser(userConverter.convertUser(userResponseDto));
		this.postRepository.save(post);

		return this.postConverter.convertPostResponse(post);
	}

	public PostResponseDto findById(Long id) {
		var post = this.postRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException(format("post id : {0}, 없는 id 입니다.", id),
					ErrorCode.POST_ID_NOT_FOUND));

		return this.postConverter.convertPostResponse(post);
	}

	public Page<PostResponseDto> findAll(Pageable pageable) {
		return this.postRepository.findAll(pageable).map(this.postConverter::convertPostResponse);
	}

	@Transactional
	public PostResponseDto update(Long id, PostUpdateRequestDto postUpdateRequest) {
		var post = this.postRepository.findById(id)
			.orElseThrow(() ->
				new NotFoundEntityByIdException(format("post id : {0}, 없는 id 입니다.", id),
					ErrorCode.POST_ID_NOT_FOUND));
		post.update(postUpdateRequest.title(), postUpdateRequest.content());

		return this.postConverter.convertPostResponse(post);
	}
}