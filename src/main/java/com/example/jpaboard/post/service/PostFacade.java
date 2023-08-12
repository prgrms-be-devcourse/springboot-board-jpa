package com.example.jpaboard.post.service;

import com.example.jpaboard.member.service.MemberService;
import com.example.jpaboard.member.service.dto.MemberFindResponse;
import com.example.jpaboard.post.service.dto.PostFindRequest;
import com.example.jpaboard.post.service.dto.PostResponse;
import com.example.jpaboard.post.service.dto.PostSaveRequest;
import com.example.jpaboard.post.service.dto.PostUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class PostFacade {
    private final MemberService memberService;
    private final PostService postService;

    public PostFacade(MemberService memberService, PostService postService){
        this.memberService = memberService;
        this.postService = postService;
    }

    public PostResponse createPost(PostSaveRequest postSaveRequest) {
        MemberFindResponse member = memberService.findById(postSaveRequest.memberId());

        return postService.savePost(member,postSaveRequest);
    }

    public PostResponse findById(Long postId) {
        return postService.findById(postId);
    }

    public Slice<PostResponse> findAllByFilter(PostFindRequest postFindRequest, Pageable pageable) {
        return postService.findAllByFilter(postFindRequest, pageable);
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        return postService.updatePost(id, request);
    }

}
