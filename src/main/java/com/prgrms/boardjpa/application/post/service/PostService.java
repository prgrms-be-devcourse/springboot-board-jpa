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
import com.prgrms.boardjpa.application.user.repository.UserRepository;
import com.prgrms.boardjpa.core.commons.exception.NotExistException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
public class PostService {
	private final PostConverter postConverter;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public PostService(PostConverter postConverter, PostRepository postRepository,
		UserRepository userRepository) {
		this.postConverter = postConverter;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public PostDto.PostInfo store(String title, User writer, String content) {
		Post post = new Post(title, writer, content);

		log.info("게시글 생성 성공 - postId : " + post.getId());

		return postConverter.entity2CreateResponse(
			postRepository.save(post)
		);
	}

	@Transactional
	public PostDto.PostInfo store(String title, Long writerId, String content) {
		return userRepository.findById(writerId)
			.map(writer -> this.store(title, writer,
				content))// FIXME : same class 내에서 @Transactional method 호출시 , this.store()로 호출되는 메소드에 설정한 @Transactional 설정은 적용되지 않는다 (현재는 동일한 설정을 사용하고 있기에 별다른 이상은 없을 것이다 )
			.orElseThrow(() -> {
				log.info("존재하지 않는 사용자의 게시글 작성 요청 : writerId {}", writerId);
				return new AuthorizationFailException();
			});
	}

	@Transactional
	public PostDto.PostInfo edit(String title, Long postId, String content) {
		return postRepository.findById(postId)
			.map(post -> post.edit(title, content))
			.map(post -> {
				log.info("게시글 변경 성공 - pstId :  " + post.getId());
				return post;
			})
			.map(postRepository::save)
			.map(postConverter::entity2CreateResponse)
			.orElseThrow(NotExistException::new);
	}

	public PostDto.PostInfo getById(Long postId) {
		return postRepository.findById(postId)
			.map(postConverter::entity2CreateResponse)
			.orElseThrow(NotExistException::new);
	}

	public List<PostDto.PostInfo> getAllByWriterName(String writerName, Pageable pageable) {
		return postRepository.findAllByCreatedBy(writerName, pageable)
			.stream()
			.map(postConverter::entity2CreateResponse)
			.collect(Collectors.toList());
	}

	public List<PostDto.PostInfo> getAllByPaging(Pageable pageable) {
		return postRepository.findAllBy(pageable)
			.stream()
			.map(postConverter::entity2CreateResponse)
			.collect(Collectors.toList());
	}

	public List<PostDto.PostInfo> getAll() {
		return postRepository.findAll()
			.stream()
			.map(postConverter::entity2CreateResponse)
			.collect(Collectors.toList());
	}

	@Transactional
	public PostDto.PostInfo toggleLike(User user, Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(NotExistException::new);

		post.like(user);
		log.info("게시글 " + postId + " 에 대하여 사용자 " + user.getId() + "의 좋아요 상태를 변경한다");

		return postConverter.entity2Info(post, user);
	}

	public List<PostDto.PostInfo> getAllLikedBy(User user) {
		return postRepository.findAllLikedBy(user).stream()
			.map(post -> postConverter.entity2Info(post, user))
			.collect(Collectors.toList());
	}
}
