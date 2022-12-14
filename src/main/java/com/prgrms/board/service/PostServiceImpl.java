package com.prgrms.board.service;

import com.prgrms.board.domain.Member;
import com.prgrms.board.domain.Post;
import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostResponseDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.service.converter.MemberConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final MemberConverter converter;

    @Override
    public Long register(PostCreateDto postCreateDto) {
        Member member = memberRepository.findById(postCreateDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("잘못된 사용자 정보입니다."));

        Post newPost = converter.createPostFromDto(postCreateDto);
        newPost.registerMember(member);

        postRepository.save(newPost);

        return newPost.getId();
    }

    @Override
    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("잘못된 게시글 정보입니다."));
        return converter.PostEntityToDto(post);
    }
}
