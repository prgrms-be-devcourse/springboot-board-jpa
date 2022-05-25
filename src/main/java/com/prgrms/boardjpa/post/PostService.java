package com.prgrms.boardjpa.post;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.post.dto.PostDto;
import com.prgrms.boardjpa.user.User;
import com.prgrms.boardjpa.user.UserRepository;
import com.prgrms.boardjpa.user.exception.AuthenticationFailException;

@Service
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository; // TODO: Repository 을 주입받아야 할 지, Service 주입을 받아야 할 지?

	public PostService(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public PostDto.Info create(String title, User writer, String content) {
		Post post = createPost(title, writer, content);

		return new PostDto.Info(
			postRepository.save(post)
		);
	}

	@Transactional
	public PostDto.Info create(String title, Long writerId, String content) {
		User writer = userRepository.findById(writerId)
			.orElseThrow(AuthenticationFailException::new);

		return this.create(title, writer, content);
	}

	@Transactional
	public PostDto.Info edit(String title, Long postId, String content) {
		Post foundPost = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		foundPost.edit(title, content);

		return new PostDto.Info(
			postRepository.save(foundPost)
		);
	}

	public PostDto.Info getById(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		return new PostDto.Info(post);
	}

	public List<PostDto.Info> getAllByWriterName(String writerName, Pageable pageable) {
		return postRepository.findAllByCreatedBy(writerName, pageable)
			.stream()
			.map(p -> new PostDto.Info(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	public List<PostDto.Info> getAllByPaging(Pageable pageable) {
		return postRepository.findAllBy(pageable)
			.stream()
			.map(p -> new PostDto.Info(
				p.getTitle(),
				p.getContent(),
				p.getCreatedBy()))
			.collect(Collectors.toList());
	}

	public List<PostDto.Info> getAll() {
		return postRepository.findAll()
			.stream()
			.map(p -> new PostDto.Info(
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
