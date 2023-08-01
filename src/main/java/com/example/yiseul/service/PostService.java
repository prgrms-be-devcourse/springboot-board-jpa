package com.example.yiseul.service;

import com.example.yiseul.converter.PostConverter;
import com.example.yiseul.domain.Member;
import com.example.yiseul.domain.Post;
import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostResponseDto;
import com.example.yiseul.dto.post.PostUpdateRequestDto;
import com.example.yiseul.global.exception.ErrorCode;
import com.example.yiseul.global.exception.MemberException;
import com.example.yiseul.global.exception.PostException;
import com.example.yiseul.repository.MemberRepository;
import com.example.yiseul.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto createRequestDto){
        Member member = memberRepository.findById(createRequestDto.memberId()).orElseThrow(IllegalArgumentException::new);
        Post post = PostConverter.convertPost(member, createRequestDto);
        postRepository.save(post);

        return PostConverter.convertPostDto(post);
    }

    public Page<PostResponseDto> getPosts(Pageable pageable) {

        return postRepository.findAll(pageable)
                .map(post -> PostConverter.convertPostDto(post));
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        return PostConverter.convertPostDto(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto updateRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND));

        post.updatePost(updateRequestDto.title(), updateRequestDto.content());
    }

    @Transactional
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostException(ErrorCode.POST_NOT_FOUND);
        }

        memberRepository.deleteById(postId);
    }

}
