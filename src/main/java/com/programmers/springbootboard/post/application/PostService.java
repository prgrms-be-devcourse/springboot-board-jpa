package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.PostDetailResponse;
import com.programmers.springbootboard.post.dto.PostInsertRequest;
import com.programmers.springbootboard.post.dto.PostUpdateRequest;
import com.programmers.springbootboard.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostConverter postConverter;
    private final PostRepository postRepository;

    @Transactional
    public void insert(Email email, PostInsertRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        Post post = postConverter.toPost(request, member);
        member.getPosts().addPost(post);
        post.addByInformation(member.getId());
    }

    @Transactional
    public void update(Email email, Long id, PostUpdateRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("");
                });
        post.update(new Title(request.getTitle()), new Content(request.getContent()));
        post.lastModifiedId(member.getId());
    }

    @Transactional(readOnly = true)
    public PostDetailResponse post(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("");
                });
        return postConverter.toPostDetailResponse(post);
    }

    @Transactional(readOnly = true)
    public List<PostDetailResponse> posts() {
        return postRepository.findAll()
                .stream()
                .map(postConverter::toPostDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id, Email email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        Post post = postRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("");
                });
        if (!member.getPosts().ownPost(post)) {
            throw new RuntimeException();
        }
        member.getPosts().deletePost(post);
    }
}
