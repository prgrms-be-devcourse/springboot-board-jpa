package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.exception.NotFoundException;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.dto.response.PostDeleteResponse;
import com.programmers.springbootboard.post.dto.response.PostDetailResponse;
import com.programmers.springbootboard.post.dto.response.PostInsertResponse;
import com.programmers.springbootboard.post.dto.response.PostUpdateResponse;
import com.programmers.springbootboard.post.dto.bundle.PostDeleteBundle;
import com.programmers.springbootboard.post.dto.bundle.PostFindBundle;
import com.programmers.springbootboard.post.dto.bundle.PostInsertBundle;
import com.programmers.springbootboard.post.dto.bundle.PostUpdateBundle;
import com.programmers.springbootboard.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    // private final MemberService memberService; // TODO :: 파사드 레이어를 만들어서 써보자!
    private final PostConverter postConverter;
    private final PostRepository postRepository;

    @Transactional
    public PostInsertResponse insert(PostInsertBundle dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });

        Post post = postConverter.toPost(dto);
        post.addPost(member);

        Post insertedPost = postRepository.save(post);
        return postConverter.toPostInsertResponseDto(insertedPost);
    }

    @Transactional
    public PostDeleteResponse deleteByEmail(PostDeleteBundle serviceDto) {
        Member member = memberRepository.findByEmail(serviceDto.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });

        Post post = member.getPosts().findPostById(serviceDto.getId());
        member.getPosts().deletePost(post);

        return postConverter.toPostDeleteResponse(post.getId(), member.getEmail());
    }

    @Transactional
    public PostUpdateResponse update(PostUpdateBundle serviceDto) {
        Member member = memberRepository.findByEmail(serviceDto.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });

        Post post = member.getPosts().findPostById(serviceDto.getId());

        post.update(
                serviceDto.getTitle(),
                serviceDto.getContent(),
                member.getId()
        );

        return postConverter.toPostUpdateResponseDto(post);
    }


    @Transactional(readOnly = true)
    public PostDetailResponse findById(PostFindBundle serviceDto) {
        return postRepository.findById(serviceDto.getId())
                .map(postConverter::toPostDetailResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
    }

    @Transactional(readOnly = true)
    public Page<PostDetailResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::toPostDetailResponse);
    }

    @Transactional(readOnly = true)
    public long count() {
        return postRepository.count();
    }
}
