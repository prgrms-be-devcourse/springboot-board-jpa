package com.prgrms.devcourse.springjpaboard.domain.post.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.exeception.PostNotFoundException;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final PostRepository postRepository;

	public Slice<Post> findAll(Long cursorId, Integer size) {
		Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
		return postRepository.findByIdLessThan(cursorId, pageable);
	}

	public Post findById(Long id) {
		return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
	}

	@Transactional
	public Long create(Post post) {
		return postRepository.save(post).getId();
	}

	@Transactional
	public void update(Long id, String title, String content) {

		Post savedPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

		savedPost.updatePost(title, content);

	}
}
