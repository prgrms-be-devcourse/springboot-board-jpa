package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	 *     post 저장
	 * </pre>
	 * @param postCreateRequest - post를 작성한 userId와 저장할 Post의 title과 content를 저장한 Dto
	 * @return status : created , body : postCreateResponseDto
	 */
	@PostMapping(consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostCreateResponse> create(@Valid @RequestBody PostCreateRequest postCreateRequest) {

		PostCreateResponse postCreateResponse = postFacade.create(postCreateRequest);

		return new ResponseEntity<>(postCreateResponse, HttpStatus.CREATED);

	}

	/**
	 * <pre>
	 *     post 전체 조회
	 * </pre>
	 * @param cursorId 마지막에 조회한 Post의 id
	 * @param size 페이지 사이즈
	 * @return status : ok , body : postSearchResponses
	 */
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostSearchResponses> findAll(@RequestParam("cursorId") Long cursorId,
		@RequestParam("size") Integer size) {

		PostSearchResponses postSearchResponses = postFacade.findAll(cursorId, size);

		return ResponseEntity.ok(postSearchResponses);
	}

	/**
	 * <pre>
	 *     post 단건 조회
	 * </pre>
	 * @param id - 조회할 Post의 id
	 * @return status : ok , body : postResponseDto
	 */
	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostSearchResponse> findById(@PathVariable(name = "id") Long id) {

		PostSearchResponse postResponse = postFacade.findById(id);

		return ResponseEntity.ok(postResponse);
	}

	/**
	 * <pre>
	 *     post 수정
	 * </pre>
	 * @param id - 수정할 Post의 id
	 * @param postUpdateRequest - 수정할 Post의 title과 content를 저장한 Dto
	 * @return status : ok , body : void
	 */
	@PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable(name = "id") Long id,
		@Valid @RequestBody PostUpdateRequest postUpdateRequest) {
		postFacade.update(id, postUpdateRequest);

		return ResponseEntity.ok().build();
	}
}
