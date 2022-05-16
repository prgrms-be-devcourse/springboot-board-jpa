package com.programmers.board.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.board.controller.PostDto;
import com.programmers.board.domain.Post;
import com.programmers.board.exception.ServiceException;
import com.programmers.board.repository.PostRepository;

@Transactional(readOnly = true)
@Service
public class PostService {

	private final PostRepository postRepository;
	private final PostConverter converter;

	public PostService(PostRepository postRepository, PostConverter converter) {
		this.postRepository = postRepository;
		this.converter = converter;
	}

	@Transactional
	public PostDto.Response save(PostDto.Save postDto) {
		Post saving = converter.toDomain(postDto);
		Post saved = postRepository.save(saving);

		return converter.toResponse(saved);
	}

	public PostDto.Response findOne(Long postId) {
		Post foundPost = postRepository.findById(postId)
				.orElseThrow(
						() -> new ServiceException.NotFoundResource(postId + "를 가진 게시글을 찾을 수 없습니다.")
				);
		return converter.toResponse(foundPost);
	}
}
