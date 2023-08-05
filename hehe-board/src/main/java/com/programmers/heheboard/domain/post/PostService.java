package com.programmers.heheboard.domain.post;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.heheboard.domain.user.User;
import com.programmers.heheboard.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public PostResponseDto createPost(CreatePostRequestDto createPostRequestDto) {
		Post post = createPostRequestDto.toEntity();
		User user = userRepository.findById(createPostRequestDto.userId())
			.orElseThrow(() -> new RuntimeException("User Not Found!"));

		post.setUser(user);

		return PostResponseDto.toResponse(postRepository.save(post));
	}

	@Transactional
	public PostResponseDto findPost(Long postId) {
		Post retrievedPost = postRepository.findById(postId)
			.orElseThrow(() -> new RuntimeException("Post Not Found!"));

		return PostResponseDto.toResponse(retrievedPost);
	}

	@Transactional
	public Slice<PostResponseDto> getPosts(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		return postRepository.findSliceBy(pageRequest)
			.map(PostResponseDto::toResponse);
	}

	@Transactional
	public PostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto) {
		Post retrievedPost = postRepository.findById(postId)
			.orElseThrow(() -> new RuntimeException("Post Not Found!"));

		retrievedPost.changeTitle(updatePostRequestDto.title());
		retrievedPost.changeContents(updatePostRequestDto.content());

		return PostResponseDto.toResponse(retrievedPost);
	}
}
