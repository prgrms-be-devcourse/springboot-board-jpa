package com.programmers.board.Service;

import java.util.function.Supplier;

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
						supplyNotFoundException(postId)
				);
		return converter.toResponse(foundPost);
	}

	public void deleteOne(Long postId) {
		Post removing = postRepository.findById(postId)
				.orElseThrow(
						supplyNotFoundException(postId)
				);
		postRepository.delete(removing);
	}

	public PostDto.Response update(PostDto.Update updateDto) {
		Post target = postRepository.findById(updateDto.id())
				.orElseThrow(
						supplyNotFoundException(updateDto.id())
				);
		Post updateDtoToPost = converter.toDomain(updateDto);
		Post update = target.update(updateDtoToPost);

		return converter.toResponse(update);
	}

	private Supplier<ServiceException.NotFoundResource> supplyNotFoundException(Long postId) {
		return () -> new ServiceException.NotFoundResource(postId + "를 가진 게시글을 찾을 수 없습니다.");
	}

	@Transactional
	public void softDeleteOne(Long postId) {
		Post target = postRepository.findById(postId)
				.orElseThrow(
						supplyNotFoundException(postId)
				);
		target.softDelete();
	}
}
