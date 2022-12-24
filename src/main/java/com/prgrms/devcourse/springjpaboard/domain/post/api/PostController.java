package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

	private final PostFacade postFacade;

	/**
	 * <pre>
	 *     커서 기반 페이지 네이션으로 Post를 조회하여 리턴합니다.
	 * </pre>
	 * @param cursorId 마지막에 조회한 Post의 id
	 * @param size 페이지 사이즈
	 * @return status ok Slice로 조회한 Post 인스턴스를 가지는 Dto
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostSearchResponses> findAll(@RequestParam("cursorId") Long cursorId,
		@RequestParam("size") Integer size) {

		Pageable pageable = PageRequest.of(0, size);

		PostSearchResponses postSearchResponses = postFacade.findAll(cursorId, pageable);

		return ResponseEntity.ok(postSearchResponses);
	}

	/**
	 * <pre>
	 *     단건 조회할 Post id를 사용하여 Post를 조회합니다.
	 * </pre>
	 * @param id - 조회할 Post의 id
	 * @return status ok 와 postResponseDto를 ResponseEntity에 담아 리턴합니다.
	 */
	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostSearchResponse> findById(@PathVariable(name = "id") Long id) {

		PostSearchResponse postResponseDto = postFacade.findById(id);

		return ResponseEntity.ok(postResponseDto);
	}

	/**
	 * <pre>
	 *     postSaveDto를 사용하여 Post를 만들어 저장합니다.
	 * </pre>
	 * @param postSaveDto - post를 작성한 userId와 저장할 Post의 title과 content를 저장한 Dto
	 * @return status ok를 ResponseEntity에 담아 리턴합니다.
	 */
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostCreateResponse> create(@Valid @RequestBody PostCreateRequest postSaveDto) {

		PostCreateResponse postCreateResponseDto = postFacade.create(postSaveDto);

		return new ResponseEntity<>(postCreateResponseDto, HttpStatus.CREATED);

	}

	/**
	 * <pre>
	 *     수정할 Post id를 사용하여 Post를 수정합니다.
	 * </pre>
	 * @param id - 수정할 Post의 id
	 * @param postUpdateRequest - 수정할 Post의 title과 content를 저장한 Dto
	 * @return status ok를 ResponseEntity에 담아 리턴합니다.
	 */
	@PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable(name = "id") Long id,
		@Valid @RequestBody PostUpdateRequest postUpdateRequest) {
		postFacade.update(id, postUpdateRequest);

		return ResponseEntity.ok().build();
	}
}
