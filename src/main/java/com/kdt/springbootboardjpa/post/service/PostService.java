package com.kdt.springbootboardjpa.post.service;

import com.kdt.springbootboardjpa.global.exception.NotFoundEntityException;
import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import com.kdt.springbootboardjpa.post.domain.Post;
import com.kdt.springbootboardjpa.post.service.dto.CreatePostRequest;
import com.kdt.springbootboardjpa.post.service.dto.PostResponse;
import com.kdt.springbootboardjpa.post.repository.PostRepository;
import com.kdt.springbootboardjpa.post.service.converter.PostConverter;
import com.kdt.springbootboardjpa.post.service.dto.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.springbootboardjpa.global.exception.ExceptionMessage.MEMBER_NOT_EXIST;
import static com.kdt.springbootboardjpa.global.exception.ExceptionMessage.POST_NOT_EXIST;

@Service
public class PostService {

    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final MemberRepository memberJpaRepository;

    public PostService(PostConverter postConverter, PostRepository postRepository, MemberRepository memberJpaRepository) {
        this.postConverter = postConverter;
        this.postRepository = postRepository;
        this.memberJpaRepository = memberJpaRepository;
    }

    @Transactional
    public PostResponse save(CreatePostRequest createPostRequest) {
        // localhost api 강제적으로 url 입력할 때
        Member member = memberJpaRepository.findById(createPostRequest.getMemberId())
                .orElseThrow(() -> new NotFoundEntityException(MEMBER_NOT_EXIST));
        Post post = postRepository.save(postConverter.createRequestToPost(createPostRequest, member));
        return postConverter.postToResponse(post);
    }

    // @Transactional VS @Transactional(readOnly = true)
    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(POST_NOT_EXIST));
        return postConverter.postToResponse(post);
    }

    @Transactional
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::postToResponse);
    }

    @Transactional
    public PostResponse update(Long id, UpdatePostRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(POST_NOT_EXIST));
        post.changePost(updateRequest.title(), updateRequest.content());
        // postRepository.save(post); 더티 체킹됨
        return postConverter.postToResponse(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(POST_NOT_EXIST));
        postRepository.delete(post);
    }
}