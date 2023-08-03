package com.example.jpaboard.post.service;

import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.domain.PostRepository;
import com.example.jpaboard.post.exception.EntityNotFoundException;
import com.example.jpaboard.post.service.dto.*;
import com.example.jpaboard.post.service.mapper.PostMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class PostService {
    private final PostRepository postRepository;
    private final PostMapper mapper;

    public PostService(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public PostResponses findAllBy(Pageable pageable) {
        return PostResponses.of(postRepository.findAll(pageable));
    }

    public PostResponse findById(Long postId){
        Post findPost = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 post가 존재하지 않습니다."));
        return new PostResponse(findPost);
    }

    @Transactional
    public PostResponse savePost(SaveRequest request){
        Member findMember = memberService.findById(request.memberId());// 나중에 별님이랑 얘기할거
        return new PostResponse(postRepository.save(mapper.to(request, findMember)));
    }

    @Transactional
    public PostResponse updatePost(UpdateRequest request){
        Post findPost = postRepository.findById(request.postId())
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        findPost.setTitle(request.title());
        findPost.setContent(request.content());
        return new PostResponse(findPost);
    }

}
