package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.PostSummaryResponseDto;
import com.spring.board.springboard.post.domain.dto.PostDetailResponseDto;
import com.spring.board.springboard.post.exception.NoPostException;
import com.spring.board.springboard.post.repository.PostRepository;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.dto.MemberSummaryResponseDto;
import com.spring.board.springboard.user.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final MemberService memberService;

    public PostService(PostRepository postRepository, MemberService memberService) {
        this.postRepository = postRepository;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public List<PostSummaryResponseDto> getAll(Pageable pageable) {
        List<Post> postList = postRepository.findAll(pageable)
                .getContent();
        return postList.stream()
                .map(post -> new PostSummaryResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getMemberName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getOne(Integer postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new NoPostException(
                            MessageFormat.format("해당 id ({0})의 게시물이 없습니다.", postId));
                });

        return new PostDetailResponseDto(
                findPost, new MemberSummaryResponseDto(findPost.getMember())
        );
    }

    @Transactional
    public PostDetailResponseDto createPost(PostCreateRequestDto postCreateRequestDto) {
        Member findMember = memberService.getMember(postCreateRequestDto.memberId());

        Post post = postCreateRequestDto.toEntity(findMember);
        postRepository.save(post);

        return new PostDetailResponseDto(post, new MemberSummaryResponseDto(findMember));
    }

    @Transactional
    public PostDetailResponseDto update(Integer postId, PostCreateRequestDto postCreateRequestDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new NoPostException(
                            MessageFormat.format("(id: {0})에 해당하는 게시물이 없습니다.", postId)
                    );
                });

        findPost.change(
                postCreateRequestDto.title(),
                postCreateRequestDto.content()
        );

        return new PostDetailResponseDto(findPost, new MemberSummaryResponseDto(findPost.getMember()));
    }
}
