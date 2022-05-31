package com.prgrms.boardjpa.post;

import static com.prgrms.boardjpa.post.PostDto.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.commons.exception.NotExistException;
import com.prgrms.boardjpa.user.domain.User;
import com.prgrms.boardjpa.user.UserService;
import com.prgrms.boardjpa.user.exception.AuthorizationFailException;

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
	public PostInfo store(String title, User writer, String content) {
		Post post = createPost(title, writer, content);

		return PostInfo.from(
			postRepository.save(post)
		);
	}

	@Transactional
	public PostInfo store(String title, Long writerId, String content) {
		try {
			User writer = userService.getById(writerId);

			return this.store(title, writer, content);
		} catch (NotExistException e) {
			log.info("존재하지 않는 사용자의 게시글 작성 요청 {}", writerId);

			throw new AuthorizationFailException();
		}
	}

	@Transactional
	public PostInfo edit(String title, Long postId, String content) {
		Post foundPost = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		foundPost.edit(title, content);

		return PostInfo.from(
			postRepository.save(foundPost)
		);
	}

	public PostInfo getById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		return PostInfo.from(post);
	}

	public List<PostInfo> getAllByWriterName(String writerName, Pageable pageable) {
		return postRepository.findAllByCreatedBy(writerName, pageable)
			.stream()
			.map(p -> new PostInfo(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	public List<PostInfo> getAllByPaging(Pageable pageable) {
		return postRepository.findAllBy(pageable)
			.stream()
			.map(p -> new PostInfo(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	public List<PostInfo> getAll() {
		return postRepository.findAll()
			.stream()
			.map(p -> new PostInfo(
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
