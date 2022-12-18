package com.prgrms.devcourse.springjpaboard.domain.post.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	private static final int DEFAULT_SIZE = 10;

	private final PostRepository postRepository;

	@Transactional
	public List<Post> findAll(Long cursorId, Integer size) {

		if (size == null)
			size = DEFAULT_SIZE;
		PageRequest pageRequest = PageRequest.of(0, size);

		return cursorId == null ?
			this.postRepository.findAllByOrderByIdDesc(pageRequest) :
			this.postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageRequest);
	}

	public Long getLastIdOfList(List<Post> postList) {
		return postList.isEmpty() ?
			null : postList.get(postList.size() - 1).getId();
	}

	@Transactional
	public boolean hasNext(Long cursorId) {
		if (cursorId == null)
			return false;
		return postRepository.existsByIdLessThan(cursorId);
	}

	@Transactional
	public Post findById(Long id) {
		return postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public void create(Post post) {
		postRepository.save(post);
	}

	@Transactional
	public void update(Long id, Post post) {

		Post savedPost = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		savedPost.updatePost(post);

	}
}
