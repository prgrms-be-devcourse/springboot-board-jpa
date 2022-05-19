package org.prgms.board.domain.post.service;

import javax.transaction.Transactional;

import org.prgms.board.domain.post.domain.Post;
import org.prgms.board.domain.post.domain.PostRepository;
import org.prgms.board.domain.user.domain.User;
import org.prgms.board.domain.user.domain.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Override
	public Page<Post> getPostPage(Pageable pageable) {
		return postRepository.findAll(pageable);

	}

	@Override
	public Post findPost(Long postId) {
		return postRepository.findById(postId)
			.orElseThrow(() -> new IllegalStateException("해당하는 게시글이 존재하지 않습니다. postId : " + postId));
	}

	@Override
	public Post writePost(String title, String content, Long writerId) {
		final User writer = userRepository.findById(writerId)
			.orElseThrow(() -> new IllegalStateException("해당하는 사용자가 존재하지 않습니다. userId : " + writerId));

		return postRepository.save(Post.create(title, content, writer));
	}

	@Override
	public void updatePost(String title, String content, Long postId) {
		final Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalStateException("해당하는 게시글이 존재하지 않습니다. postId : " + postId));

		findPost.updatePost(title, content);
	}

}
