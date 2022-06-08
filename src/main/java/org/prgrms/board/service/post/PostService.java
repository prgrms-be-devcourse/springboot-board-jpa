package org.prgrms.board.service.post;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;

import org.prgrms.board.domain.post.Post;
import org.prgrms.board.domain.post.PostRepository;
import org.prgrms.board.domain.user.User;
import org.prgrms.board.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;

	public PostService(PostRepository postRepository, UserService userService) {
		this.postRepository = postRepository;
		this.userService = userService;
	}

	public List<Post> findAll(long offset, int limit) {
		return postRepository.findAll(offset, limit);
	}

	public long count() {
		return postRepository.count();
	}

	public Post findById(Long id) {
		checkArgument(id != null, "postId must be provided.");

		return postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Could not found post with postId=" + id));
	}

	@Transactional
	public Post write(String title, String content, Long writerId) {
		checkArgument(isNotEmpty(title), "title must be provided.");
		checkArgument(isNotEmpty(content), "content must be provided.");
		checkArgument(writerId != null, "writerId must be provided.");

		User writer = userService.findById(writerId);
		Post post = new Post(title, content, writer);

		return postRepository.save(post);
	}

	@Transactional
	public Post modify(Long id, String title, String content) {
		checkArgument(id != null, "postId must be provided.");
		checkArgument(isNotEmpty(title), "title must be provided.");
		checkArgument(isNotEmpty(content), "content must be provided.");

		return postRepository.findById(id)
			.map(post -> {
				post.modifyTitleAndContent(title, content);
				return postRepository.save(post);
			})
			.orElseThrow(() -> new IllegalArgumentException("Could not found post with postId=" + id));
	}
}
