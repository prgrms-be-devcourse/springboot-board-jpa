package com.prgrms.domain.post;

import static com.prgrms.dto.PostDto.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.prgrms.domain.user.User;
import com.prgrms.domain.user.UserService;
import com.prgrms.global.error.ErrorCode;
import com.prgrms.global.exception.AuthenticationFailedException;
import com.prgrms.global.exception.PostNotFoundException;

import jakarta.validation.Valid;

@Validated
@Service
public class PostService {

	private final PostRepository postRepository;

	private final UserService userService;

	public PostService(PostRepository postRepository, UserService userService) {

		this.postRepository = postRepository;
		this.userService = userService;
	}

	@Transactional(readOnly = true)
	public Response findPostById(Long id) {

		return postRepository.findById(id)
			.map(Response::from)
			.orElseThrow(() -> new PostNotFoundException("id: " + id, ErrorCode.POST_NOT_FOUND));
	}

	@Transactional
	public Response insertPost(Long userId, @Valid Request postDto) {

		User user = userService.findUserById(userId).toUser();
		Post saved = postRepository.save(postDto.toPost(user));

		return Response.from(saved);

	}

	@Transactional
	public Response updatePost(Long postId, Long userId, @Valid Update postDto) {

		checkOwnPost(postId, userId);
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException("id: " + postId, ErrorCode.POST_NOT_FOUND));

		post.update(postDto.content(), postDto.title());

		return Response.from(post);
	}

	@Transactional(readOnly = true)
	public ResponsePostDtos getPostsByPage(Pageable pageable) {

		List<Response> responses = postRepository.findAll(pageable)
			.map(Response::from)
			.stream().toList();

		return new ResponsePostDtos(responses);
	}

	@Transactional
	public void deletePost(Long postId, Long userId) {
		checkOwnPost(postId, userId);
		postRepository.deleteById(postId);
	}

	private void checkOwnPost(Long postId, Long userId) {

		Response post = findPostById(postId);

		if (!Objects.equals(userId, post.userId())) {
			throw new AuthenticationFailedException("본인 게시글만 수정, 삭제할 수 있습니다",
				ErrorCode.AUTHENCTICATION_FAILED);
		}
	}

}
