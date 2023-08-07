package com.programmers.heheboard.domain.post;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.heheboard.domain.user.User;
import com.programmers.heheboard.domain.user.UserRepository;
import com.programmers.heheboard.global.codes.ErrorCode;
import com.programmers.heheboard.global.exception.GlobalRuntimeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public PostResponseDto createPost(CreatePostRequestDto createPostRequestDto) {
		Post post = createPostRequestDto.toEntity();
		User user = userRepository.findById(createPostRequestDto.getUserId())
			.orElseThrow(() -> new GlobalRuntimeException(ErrorCode.USER_NOT_FOUND));

		post.attachUser(user);

		return PostResponseDto.toResponse(postRepository.save(post));
	}

	@Transactional(readOnly = true)
	public PostResponseDto findPost(Long postId) {
		Post retrievedPost = postRepository.findById(postId)
			.orElseThrow(() -> new GlobalRuntimeException(ErrorCode.POST_NOT_FOUND));

		return PostResponseDto.toResponse(retrievedPost);
	}

	@Transactional(readOnly = true)
	public Slice<PostResponseDto> getPosts(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		return postRepository.findSliceBy(pageRequest)
			.map(PostResponseDto::toResponse);
	}

	@Transactional
	public PostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {
		Post retrievedPost = postRepository.findById(postId)
			.orElseThrow(() ->new GlobalRuntimeException(ErrorCode.POST_NOT_FOUND));

		String newTitle = updatePostRequestDto.getTitle();
		String newContent = updatePostRequestDto.getContent();

		retrievedPost.updatePost(newTitle, newContent);

		return PostResponseDto.toResponse(retrievedPost);
	}
}
