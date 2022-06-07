package org.programmers.kdtboard.service;

import static java.text.MessageFormat.*;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.converter.PostConverter;
import org.programmers.kdtboard.domain.post.Post;
import org.programmers.kdtboard.domain.post.PostRepository;
import org.programmers.kdtboard.dto.PostDto.Response;
import org.programmers.kdtboard.exception.NotFoundEntityByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PostService {

	private final PostRepository postRepository;
	private final UserService userService;
	private final PostConverter postConverter;

	public PostService(PostRepository postRepository, UserService userService,
		PostConverter postConverter) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.postConverter = postConverter;
	}

	@Transactional
	public Response create(String title, String content, Long userId) {
		var post = Post.builder().title(title)
			.content(content)
			.build();
		post.setUser(this.userService.findEntityById(userId));
		this.postRepository.save(post);

		return this.postConverter.convertPostResponse(post);
	}

	public Response findById(Long id) {
		var post = this.postRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException(format("post id : {0}, 없는 id 입니다.", id),
					ErrorCode.POST_ID_NOT_FOUND));

		return this.postConverter.convertPostResponse(post);
	}

	public Page<Response> findAll(Pageable pageable) {
		Page<Post> postPages = this.postRepository.findAll(pageable);
		var postResponseDto = postPages.getContent().stream()
			.map(postConverter::convertPostResponse)
			.toList();

		return new PageImpl<>(postResponseDto, pageable, postPages.getTotalPages());
	}

	@Transactional
	public Response update(Long id, String title, String content) {
		var post = this.postRepository.findById(id)
			.orElseThrow(() ->
				new NotFoundEntityByIdException(format("post id : {0}, 없는 id 입니다.", id),
					ErrorCode.POST_ID_NOT_FOUND))
			.update(title, content);

		return this.postConverter.convertPostResponse(post);
	}
}
