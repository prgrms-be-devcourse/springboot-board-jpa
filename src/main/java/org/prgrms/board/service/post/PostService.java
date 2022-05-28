package org.prgrms.board.service.post;

import java.util.List;

import org.prgrms.board.domain.post.Post;
import org.prgrms.board.domain.post.PostQueryRepository;
import org.prgrms.board.domain.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PostService {

	private final PostRepository postRepository;
	private final PostQueryRepository postQueryRepository;

	public PostService(PostRepository postRepository, PostQueryRepository postQueryRepository) {
		this.postRepository = postRepository;
		this.postQueryRepository = postQueryRepository;
	}

	public List<Post> findAll(long offset, int limit) {
		return postQueryRepository.findAll(offset, limit);
	}

	public long count() {
		return postRepository.count();
	}
}
