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

	/**
	 * <pre>
	 *     현재 페이지 cursorId와 페이지 크기로 Post조회하여 List형태로 리턴합니다.
	 * </pre>
	 * @param cursorId - 마지막으로 조회한 Post id
	 * @param size - 조회할 페이지 size
	 * @return 처음 조회하는 경우 최근 Post부터 size 만큼 조회하여 리턴합니다. 이미 조회를 한 상태라면 cursorId 부터 Post를 size 만큼 조회하여 리턴합니다.
	 *
	 */
	public List<Post> findAll(Long cursorId, Integer size) {

		if (size == null)
			size = DEFAULT_SIZE;
		PageRequest pageRequest = PageRequest.of(0, size);

		return cursorId == null ?
			this.postRepository.findAllByOrderByIdDesc(pageRequest) :
			this.postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageRequest);
	}

	/**
	 * <pre>
	 *     조회한 Post list의 마지막에 있는 Post id를 반환합니다.
	 * </pre>
	 * @param postList - 조회를 한 Post들을 저장한 List
	 * @return List의 마지막 Post의 Id를 리턴합니다. List가 비어있으면 null을 리턴합니다.
	 */
	public Long getLastIdOfList(List<Post> postList) {
		return postList.isEmpty() ?
			null : postList.get(postList.size() - 1).getId();
	}

	/**
	 * <pre>
	 *     cursorId 보다 작은 id 값을 가지는 Post의 존재 유무를 true, false로 반환합니다.
	 * </pre>
	 * @param cursorId - 현재 cursor가 가르키는 Post id
	 * @return cursorId 보다 작은 id를 가지는 Post가 있으면 true, 없으면 false를 리턴합니다.
	 */
	public boolean hasNext(Long cursorId) {
		if (cursorId == null)
			return false;
		return postRepository.existsByIdLessThan(cursorId);
	}

	/**
	 * <pre>
	 *     id를 사용하여 Post를 조회하여 리턴합니다.
	 * </pre>
	 * @param id - 조회할 Post id
	 * @return id값이 일치하는 Post를 리턴합니다.
	 * @throws PostNotFoundException - 파라미터 id와 일치하는 id를 가진 Post가 없다면 발생합니다.
	 */
	public Post findById(Long id) {
		return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
	}

	/**
	 * <pre>
	 *     post를 저장합니다.
	 * </pre>
	 * @param post - 저장할 Post 인스턴스
	 */
	@Transactional
	public void create(Post post) {
		postRepository.save(post);
	}

	/**
	 * <pre>
	 *     post를 수정합니다.
	 * </pre>
	 * @param id - 수정할 Post id
	 * @param post - 수정할 Post 내용이 담긴 Post 인스턴스
	 * @throws PostNotFoundException -  파라미터 id와 일치하는 id를 가진 Post가 없다면 발생합니다.
	 */
	@Transactional
	public void update(Long id, Post post) {

		Post savedPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

		savedPost.updatePost(post);

	}
}
