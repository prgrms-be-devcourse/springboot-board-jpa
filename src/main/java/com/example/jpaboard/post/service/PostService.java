package com.example.jpaboard.post.service;

import com.example.jpaboard.member.service.MemberService;
import com.example.jpaboard.member.service.dto.MemberFindResponse;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.domain.PostRepository;
import com.example.jpaboard.post.service.dto.PostFindRequest;
import com.example.jpaboard.post.service.dto.PostResponse;
import com.example.jpaboard.post.service.dto.PostSaveRequest;
import com.example.jpaboard.post.service.dto.PostUpdateRequest;
import com.example.jpaboard.post.service.mapper.PostMapper;
import com.example.jpaboard.global.exception.EntityNotFoundException;
import com.example.jpaboard.global.exception.UnauthorizedEditException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
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

    public Slice<PostResponse> findAllByFilter(PostFindRequest postFindRequest, Pageable pageable) {
        Slice<Post> results = postRepository.findPostAllByFilter(postFindRequest.title(), postFindRequest.content(), pageable);
        return results.map(PostResponse::new);
    }

    public PostResponse findById(Long postId) {
        Post findPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 post가 존재하지 않습니다."));
        return new PostResponse(findPost);
    }

    @Transactional
    public PostResponse savePost(PostSaveRequest request) {
        MemberFindResponse findMember = memberService.findById(request.memberId());
        Post post = mapper.to(request, findMember);

        return new PostResponse(postRepository.save(post));
    }

    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 post가 존재하지 않습니다."));

        if (findPost.isNotOwner(request.memberId())){
            throw new UnauthorizedEditException("해당 게시글을 수정할 권한이 없습니다.");
        }

        findPost.changTitle(request.title());
        findPost.changeContent(request.content());

        return new PostResponse(findPost);
    }

}
