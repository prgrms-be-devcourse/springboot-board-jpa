package com.kdt.jpa.domain.post.service;

import java.text.MessageFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.service.MemberService;
import com.kdt.jpa.domain.post.PostConverter;
import com.kdt.jpa.domain.post.repository.PostRepository;
import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;
import com.kdt.jpa.domain.post.model.Post;
import com.kdt.jpa.exception.ErrorCode;
import com.kdt.jpa.exception.ServiceException;

@Transactional
@Service
public class DefaultPostService implements PostService {

	private final MemberService memberService;
	private final PostRepository postRepository;
	private final PostConverter postConverter;

	public DefaultPostService(MemberService memberService, PostRepository postRepository, PostConverter postConverter) {
		this.memberService = memberService;
		this.postRepository = postRepository;
		this.postConverter = postConverter;
	}

	@Override
	public PostResponse.WritePostResponse write(PostRequest.WritePostRequest request) {
		MemberResponse foundMember = this.memberService.findById(request.authorId());
		Post post = this.postConverter.toEntity(request, foundMember);

		return this.postConverter.toWritePostResponse(this.postRepository.save(post));
	}

	@Override
	@Transactional(readOnly = true)
	public PostResponse findById(Long id) {
		return this.postConverter.toPostResponse(
			this.postRepository.findById(id)
				.orElseThrow(
					() -> new ServiceException
						(
							MessageFormat.format("{0} - error target : {1}", ErrorCode.POST_NOT_FOUND.getMessage(), id),
							ErrorCode.POST_NOT_FOUND
						)
				)
		);
	}

	@Override
	public PostResponse.UpdatePostResponse update(Long id, PostRequest.UpdatePostRequest request) {
		Post foundPost = this.postRepository.findById(id)
			.orElseThrow(() -> new ServiceException(
				MessageFormat.format("{0} - error target : {1}", ErrorCode.POST_NOT_FOUND.getMessage(), id),
				ErrorCode.POST_NOT_FOUND)
			);
		foundPost.updateTitle(request.title());
		foundPost.updateContent(request.content());

		return this.postConverter.toUpdatePostResponse(foundPost);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PostResponse> findAll(Pageable pageable) {
		return this.postRepository.findAll(pageable)
			.map(this.postConverter::toPostResponse);
	}
}
