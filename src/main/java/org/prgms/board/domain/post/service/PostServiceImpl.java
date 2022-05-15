package org.prgms.board.domain.post.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.prgms.board.domain.post.domain.Post;
import org.prgms.board.domain.post.domain.PostRepository;
import org.prgms.board.domain.post.dto.PostDto;
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
	public Page<PostDto.Response> getPostPage(Pageable pageable) {
		return postRepository
			.findAll(pageable)
			.map(PostDto.Response::toPostResponse);

	}

	@Override
	public PostDto.Response getOnePost(Long postId) {
		final Post findPost = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalStateException("해당하는 게시글이 존재하지 않습니다. postId : " + postId));

		return PostDto.Response.toPostResponse(findPost);
	}

	@Override
	public PostDto.Response writePost(PostDto.Write writeDto) {
		final User writer = userRepository.findById(writeDto.getUserId())
			.orElseThrow(() -> new IllegalStateException("해당하는 사용자가 존재하지 않습니다. userId : " + writeDto.getUserId()));

		final Post savedPost = postRepository.save(Post.create(writeDto.getTitle(), writeDto.getContent(), writer));

		return PostDto.Response.toPostResponse(savedPost);
	}

	@Override
	public void updatePost(PostDto.Update updateDto) {
		final Post findPost = postRepository.findById(updateDto.getPostId())
			.orElseThrow(() -> new IllegalStateException("해당하는 게시글이 존재하지 않습니다. postId : " + updateDto.getPostId()));

		findPost.updatePost(updateDto.getTitle(), updateDto.getContent());
	}

}
