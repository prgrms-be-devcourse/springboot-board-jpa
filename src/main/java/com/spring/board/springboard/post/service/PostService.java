package com.spring.board.springboard.post.service;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.post.domain.dto.RequestPostDto;
import com.spring.board.springboard.post.domain.dto.ResponsePostDto;
import com.spring.board.springboard.post.exception.NoPostException;
import com.spring.board.springboard.post.repository.PostRepository;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.domain.MemberResponseDto;
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
    public List<ResponsePostDto> getAll(Pageable pageable) {
        List<Post> postList = postRepository.findAll(pageable).getContent();
        return postList.stream()
                .map(post -> new ResponsePostDto(
                        post, new MemberResponseDto(post.getMember())))
                .toList();
    }

    @Transactional(readOnly = true)
    public ResponsePostDto getOne(Integer postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new NoPostException(
                            MessageFormat.format("해당 id ({0})의 게시물이 없습니다.", postId));
                });

        return new ResponsePostDto(
                findPost, new MemberResponseDto(findPost.getMember())
        );
    }

    @Transactional
    public ResponsePostDto createPost(RequestPostDto requestPostDto) {
        Member findMember = memberService.find(requestPostDto.memberId());

        Post post = requestPostDto.toEntity(requestPostDto, findMember);
        postRepository.save(post);

        return new ResponsePostDto(post, new MemberResponseDto(findMember));
    }

    @Transactional
    public ResponsePostDto update(Integer postId, RequestPostDto requestPostDto) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> {
                    throw new NoPostException(
                            MessageFormat.format("해당 id ({0})의 게시물이 없습니다.", postId)
                    );
                });

        findPost.change(
                requestPostDto.title(),
                requestPostDto.content()
        );

        return new ResponsePostDto(findPost, new MemberResponseDto(findPost.getMember()));
    }
}
