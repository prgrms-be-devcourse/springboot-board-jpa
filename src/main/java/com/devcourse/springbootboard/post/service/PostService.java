package com.devcourse.springbootboard.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;
import com.devcourse.springbootboard.post.converter.PostConverter;
import com.devcourse.springbootboard.post.domain.Post;
import com.devcourse.springbootboard.post.dto.PostResponse;
import com.devcourse.springbootboard.post.dto.PostWriteRequest;
import com.devcourse.springbootboard.post.exception.PostNotFoundException;
import com.devcourse.springbootboard.post.repository.PostRepository;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.exception.UserNotFoundException;
import com.devcourse.springbootboard.user.repository.UserRepository;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public Page<PostResponse> findPosts(final Pageable pageable) {
		return postRepository.findAll(pageable)
			.map(PostConverter::toPostResponse);
	}

	@Transactional(readOnly = true)
	public PostResponse findPost(final Long postId) {
		return postRepository.findById(postId)
			.map(PostConverter::toPostResponse)
			.orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
	}

	@Transactional
	public PostResponse savePost(final PostWriteRequest postWriteRequest) {
		User foundUser = userRepository.findById(postWriteRequest.getUserId())
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		Post post = PostConverter.convertPostWriteRequest(postWriteRequest, foundUser);

		return PostConverter.toPostResponse(postRepository.save(post));
	}
}
