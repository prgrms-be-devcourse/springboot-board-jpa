package com.prgrms.devcourse.springjpaboard.domain.post.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFacade {

	private final PostConverter postConverter;

	private final PostService postService;

	private final UserService userService;

	/**
	 * <pre>
	 *     postSaveDto의 userId를 통해 User를 조회하고
	 *     PostConverter로 Post를 생성하여 postService의 create 메서드를 호출합니다.
	 * </pre>
	 * @param postSaveDto - post를 작성한 userId와 저장할 Post의 title과 content를 저장한 Dto
	 */
	@Transactional
	public PostCreateResponseDto create(PostCreateRequestDto postSaveDto) {

		User user = userService.findById(postSaveDto.getUserId());

		Long savedId = postService.create(postConverter.toPost(postSaveDto, user));

		return postConverter.toPostCreateResponseDto(savedId);


	}

	/**
	 * <pre>
	 *     PostConverter로 Post를 생성하여 postService의 update 메서드를 호출합니다.
	 * </pre>
	 * @param id - 수정할 Post id
	 * @param postUpdateDto - 수정할 Post의 title과 content를 저장한 Dto
	 */
	@Transactional
	public void update(Long id, PostUpdateDto postUpdateDto) {

		postService.update(id, postUpdateDto.getTitle(), postUpdateDto.getContent());

	}

	/**
	 * <pre>
	 *     커서 기반 페이지네이션을 위한 데이터를 만듭니다.
	 *     postService의 findAll 메서드를 호출하여 Post를 리턴 받습니다.
	 *     postService의 getLastIdOfList 메서드를 호출하여 현재 cursor가 가리키는 id를 리턴 받습니다.
	 *     postService의 hasNext 메서드를 호출하여 cursorId 보다 낮은 id를 가진 Post가 있는지 확인합니다.
	 * </pre>
	 * @param postRequestDto - 페이지 네이션을 하기 위한 커서 id 와 페이지 size를 저장한 Dto
	 * @return PostConverter로 PostResponseDtos를 생성하여 리턴합니다.
	 */
	public PostResponseDtos findAll(PostRequestDto postRequestDto) {

		List<Post> postList = postService.findAll(postRequestDto.getCursorId(), postRequestDto.getSize());

		Long lastIdOfList = postService.getLastIdOfList(postList);

		boolean hasNext = postService.hasNext(lastIdOfList);

		return postConverter.toPostResponseDtos(postList, lastIdOfList, hasNext);

	}

	/**
	 * <pre>
	 *     postService의 findById 메서드를 호출하여 Post를 리턴 받습니다.
	 *     리턴 받은 Post를 PostConverter를 사용하여 PostResponseDto를 생성하여 리턴합니다.
	 * </pre>
	 * @param id - 조회할 Post의 id
	 * @return PostConverter로 PostResponseDto를 생성하여 리턴합니다.
	 */
	public PostResponseDto findById(Long id) {
		return postConverter.toPostResponseDto(postService.findById(id));
	}
}
