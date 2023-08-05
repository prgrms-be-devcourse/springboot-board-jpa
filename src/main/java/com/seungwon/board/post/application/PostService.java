package com.seungwon.board.post.application;

import java.text.MessageFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seungwon.board.common.exception.InvalidRequestException;
import com.seungwon.board.common.exception.NoSuchDataException;
import com.seungwon.board.member.domain.Member;
import com.seungwon.board.member.infra.MemberRepository;
import com.seungwon.board.post.application.dto.PostRequestDto;
import com.seungwon.board.post.application.dto.PostResponseDto;
import com.seungwon.board.post.application.dto.PostSaveRequestDto;
import com.seungwon.board.post.domain.Post;
import com.seungwon.board.post.infra.PostRepository;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;

	public PostService(PostRepository postRepository, MemberRepository memberRepository) {
		this.postRepository = postRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional(readOnly = true)
	public Page<PostResponseDto> findAll(Pageable pageable) {
		return postRepository.findAll(pageable)
				.map(PostResponseDto::new);
	}

	@Transactional(readOnly = true)
	public PostResponseDto findBy(Long id){
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new NoSuchDataException(MessageFormat.format("해당 포스트가 존재하지 않습니다[id={0}] ", id)));
		return new PostResponseDto(post);
	}

	@Transactional
	public PostSaveRequestDto create(PostRequestDto postRequestDto) {
		Long userId = postRequestDto.writerId();
		Member member = memberRepository.findById(userId).orElseThrow();
		Post post = Post.builder()
				.writer(member)
				.title(postRequestDto.title())
				.content(postRequestDto.content())
				.build();
		Post result = postRepository.save(post);
		return new PostSaveRequestDto(result);
	}

	@Transactional
	public PostSaveRequestDto update(Long id, PostRequestDto postRequestDto) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new InvalidRequestException(
						MessageFormat.format("존재하지 않는 포스트에 대한 수정 요청입니다[id={0}] ", id)));
		post.modify(postRequestDto.title(), postRequestDto.content());
		return new PostSaveRequestDto(post);
	}
}
