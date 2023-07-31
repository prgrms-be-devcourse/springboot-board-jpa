package com.prgrms.board.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.request.PostsRequest;
import com.prgrms.board.domain.post.dto.response.PostResponse;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.service.UserService;
import com.prgrms.board.global.common.PageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public PageResponse<Post> getPosts(PostsRequest request) {
		Page<Post> posts = postRepository.findAll(request.toPage());
		return PageResponse.from(posts);
	}

	// public getPost() {
	//
	// }
	//
	@Transactional
	public PostResponse createPost(PostCreateRequest request) {
		User user = userService.findUserOrThrow(request.userId());
		Post post = postRepository.save(request.toEntity(user));
		return PostResponse.from(post);
	}

	//
	// @Transactional
	// public updatePost() {
	//
	// }
}
