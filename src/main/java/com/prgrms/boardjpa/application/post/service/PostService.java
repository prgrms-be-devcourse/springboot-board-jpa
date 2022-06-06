package com.prgrms.boardjpa.application.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.post.PostConverter;
import com.prgrms.boardjpa.application.post.PostDto;
import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.post.repository.PostRepository;
import com.prgrms.boardjpa.application.user.exception.AuthorizationFailException;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.service.UserService;
import com.prgrms.boardjpa.core.commons.exception.NotExistException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PostService {
	private final PostConverter postConverter;
	private final PostRepository postRepository;
	private final UserService userService;

	public PostService(PostConverter postConverter, PostRepository postRepository,
		UserService userService) {
		this.postConverter = postConverter;
		this.postRepository = postRepository;
		this.userService = userService;
	}

	@Transactional
	public PostDto.PostInfo store(String title, User writer, String content) {
		Post post = new Post(title, writer, content);

		return postConverter.entity2Info(
			postRepository.save(post)
		);
	}

	@Transactional
	public PostDto.PostInfo store(String title, Long writerId, String content) {
		try {
			User writer = userService.getById(writerId);
			// FIXME : same class 내에서 @Transactional method 호출시 , this.store()로 호출되는 메소드에 설정한 @Transactional 설정은 적용되지 않는다 (현재는 동일한 설정을 사용하고 있기에 별다른 이상은 없을 것이다 )
			return this.store(title, writer, content);
		} catch (NotExistException e) {
			log.info("존재하지 않는 사용자의 게시글 작성 요청 : writerId {}", writerId);

			throw new AuthorizationFailException();
		}
	}

	@Transactional
	public PostDto.PostInfo edit(String title, Long postId, String content) {
		Post foundPost = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		foundPost.edit(title, content);

		return postConverter.entity2Info(
			postRepository.save(foundPost)
		);
	}

	public PostDto.PostInfo getById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		return postConverter.entity2Info(post);
	}

	public List<PostDto.PostInfo> getAllByWriterName(String writerName, Pageable pageable) {
		return postRepository.findAllByCreatedBy(writerName, pageable)
			.stream()
			.map(postConverter::entity2Info)
			.collect(Collectors.toList());
	}

	public List<PostDto.PostInfo> getAllByPaging(Pageable pageable) {
		return postRepository.findAllBy(pageable)
			.stream()
			.map(postConverter::entity2Info)
			.collect(Collectors.toList());
	}

	public List<PostDto.PostInfo> getAll() {
		return postRepository.findAll()
			.stream()
			.map(postConverter::entity2Info)
			.collect(Collectors.toList());
	}
}
