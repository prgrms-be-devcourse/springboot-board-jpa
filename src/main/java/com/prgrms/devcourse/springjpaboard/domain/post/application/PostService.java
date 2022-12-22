package com.prgrms.devcourse.springjpaboard.domain.post.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
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

	private static final int DEFAULT_SIZE = 10;

	private final PostRepository postRepository;

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

	public boolean hasNext(Long cursorId) {
		if (cursorId == null)
			return false;
		return postRepository.existsByIdLessThan(cursorId);
	}

	public Post findById(Long id) {
		return postRepository.findById(id).orElseThrow(()->new PostNotFoundException());
	}

	@Transactional
	public Long create(Post post) {
		Post save = postRepository.save(post);
		return save.getId();
	}

	@Transactional
	public void update(Long id, String title, String content) {

		Post savedPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

		savedPost.updatePost(title, content);

	}
}
