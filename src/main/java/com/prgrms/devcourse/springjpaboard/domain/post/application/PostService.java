package com.prgrms.devcourse.springjpaboard.domain.post.application;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
import com.prgrms.devcourse.springjpaboard.domain.post.exeception.PostNotFoundException;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private static final int DEFAULT_SIZE = 10;

	private final PostRepository postRepository;

	public CursorResult findAll(Long cursorId, Integer size) {

		if (size == null)
			size = DEFAULT_SIZE;
		Pageable pageable = PageRequest.of(0, size + 1);

		List<Post> postList = getPosts(cursorId, pageable);
		Long nextCursorId = getNextCursorId(postList);

		return CursorResult.builder()
			.postList(postList)
			.nextCursorId(nextCursorId)
			.hasNext(hasNext(postList.size(), size))
			.build();
	}

	private Long getNextCursorId(List<Post> postList) {
		return postList.isEmpty() ?
			null : postList.get(postList.size() - 1).getId();
	}

	private List<Post> getPosts(Long cursorId, Pageable pageable) {
		return cursorId == null ?
			this.postRepository.findAllByOrderByIdDesc(pageable) :
			this.postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageable);

	}

	private boolean hasNext(int resultSize, int pageSize) {
		return resultSize - pageSize > 0;
	}

	public Post findById(Long id) {
		return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
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
