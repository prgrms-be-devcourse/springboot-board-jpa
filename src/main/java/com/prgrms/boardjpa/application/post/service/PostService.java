package com.prgrms.boardjpa.application.post.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.post.PostDto;
import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.post.repository.PostRepository;
import com.prgrms.boardjpa.core.commons.exception.NotExistException;
import com.prgrms.boardjpa.application.user.service.UserService;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.exception.AuthorizationFailException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public PostService(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}

	@Transactional
	public PostDto.PostInfo store(String title, User writer, String content) {
		Post post = createPost(title, writer, content);

		return PostDto.PostInfo.from(
			postRepository.save(post)
		);
	}

	@Transactional
	public PostDto.PostInfo store(String title, Long writerId, String content) {
		try {
			User writer = userService.getById(writerId);

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

		return PostDto.PostInfo.from(
			postRepository.save(foundPost)
		);
	}

	public PostDto.PostInfo getById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		return PostDto.PostInfo.from(post);
	}

	public List<PostDto.PostInfo> getAllByWriterName(String writerName, Pageable pageable) {
		return postRepository.findAllByCreatedBy(writerName, pageable)
			.stream()
			.map(p -> new PostDto.PostInfo(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	public List<PostDto.PostInfo> getAllByPaging(Pageable pageable) {
		return postRepository.findAllBy(pageable)
			.stream()
			.map(p -> new PostDto.PostInfo(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	public List<PostDto.PostInfo> getAll() {
		return postRepository.findAll()
			.stream()
			.map(p -> new PostDto.PostInfo(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	private Post createPost(String title, User writer, String content) {
		return Post.builder()
			.content(content)
			.writer(writer)
			.title(title)
			.build();
	}
}
