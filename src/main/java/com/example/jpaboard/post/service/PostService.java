package com.example.jpaboard.post.service;

import com.example.jpaboard.member.service.MemberService;
import com.example.jpaboard.member.service.dto.FindMemberResponse;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.domain.PostRepository;
import com.example.jpaboard.post.exception.EntityNotFoundException;
import com.example.jpaboard.post.exception.PermissionDeniedEditException;
import com.example.jpaboard.post.service.dto.*;
import com.example.jpaboard.post.service.mapper.PostMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostMapper mapper;

    public PostService(PostRepository postRepository, MemberService memberService, PostMapper mapper) {
        this.postRepository = postRepository;
        this.memberService = memberService;
        this.mapper = mapper;
    }

    public Slice<PostResponse> findPostAllByFilter(FindAllRequest findAllRequest, Pageable pageable) {

        Slice<Post> results  = postRepository.findPostAllByFilter(findAllRequest.title(), findAllRequest.content(), pageable);
        return results.map(PostResponse::new);
    }

    public PostResponse findById(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 post가 존재하지 않습니다."));
        return new PostResponse(findPost);
    }

    @Transactional
    public PostResponse savePost(SaveRequest request){
        FindMemberResponse findMember = memberService.findById(request.memberId());
        Post post = mapper.to(request, findMember);

        return new PostResponse(postRepository.save(post));
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdateRequest request){
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));
        Long postOwnerId = findPost.getMember().getId();

        if (isNotOwner(request.memberId(), postOwnerId))
            throw new PermissionDeniedEditException("해당 게시글을 수정할 권한이 없습니다.");

        findPost.setTitle(request.title());
        findPost.setContent(request.content());
        return new PostResponse(findPost);
    }

    private static boolean isNotOwner(Long memberId, Long postOwnerId) {
        return !Objects.equals(postOwnerId, memberId);
    }

}
