package com.prgrms.board.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.prgrms.board.domain.post.dto.request.PostsRequest;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.global.common.PageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public PageResponse<Post> getPosts(PostsRequest request) {
		Page<Post> posts = postRepository.findAll(request.toPage());
		return PageResponse.from(posts);
	}

	// public getPost() {
	//
	// }
	//
	// @Transactional
	// public createPost() {
	//
	// }
	//
	// @Transactional
	// public updatePost() {
	//
	// }
}
