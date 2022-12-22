package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static org.springframework.http.MediaType.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;

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
	 * @param postRequestDto - 페이지 네이션을 하기 위한 커서 id 와 페이지 size를 저장한 Dto
	 * @return status ok 와 postResponseDtos를 ResponseEntity에 담아 리턴합니다.
	 * */
	@GetMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostResponseDtos> findAll(@Valid @ModelAttribute PostRequestDto postRequestDto) {

		PostResponseDtos postResponseDtos = postFacade.findAll(postRequestDto);

		return ResponseEntity.ok(postResponseDtos);
	}

	/**
	 * <pre>
	 *     단건 조회할 Post id를 사용하여 Post를 조회합니다.
	 * </pre>
	 * @param id - 조회할 Post의 id
	 * @return status ok 와 postResponseDto를 ResponseEntity에 담아 리턴합니다.
	 */
	@GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<PostResponseDto> findById(@PathVariable(name = "id") Long id) {

		PostResponseDto postResponseDto = postFacade.findById(id);

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
	public ResponseEntity<PostCreateResponseDto> create(@Valid @RequestBody PostCreateRequestDto postSaveDto) {

		PostCreateResponseDto postCreateResponseDto = postFacade.create(postSaveDto);

		return new ResponseEntity<>(postCreateResponseDto, HttpStatus.CREATED);

	}

	/**
	 * <pre>
	 *     수정할 Post id를 사용하여 Post를 수정합니다.
	 * </pre>
	 * @param id - 수정할 Post의 id
	 * @param postUpdateDto - 수정할 Post의 title과 content를 저장한 Dto
	 * @return status ok를 ResponseEntity에 담아 리턴합니다.
	 */
	@PostMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> update(@PathVariable(name = "id") Long id,
		@Valid @RequestBody PostUpdateDto postUpdateDto) {
		postFacade.update(id, postUpdateDto);

		return ResponseEntity.ok().build();
	}
}
