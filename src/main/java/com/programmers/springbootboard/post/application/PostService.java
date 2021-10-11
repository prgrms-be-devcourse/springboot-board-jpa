package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.PostDeleteResponse;
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
    private final MemberService memberService; // 파사드 레이어를 만들어서 써보자!! or memberservice를 사용하는 것은?
    private final PostConverter postConverter;
    private final PostRepository postRepository;

    @Transactional
    public PostDetailResponse insert(Email email, PostInsertRequest request) {
        Member member = memberService.findByEmail(email);
        Post post = postConverter.toPost(request, member);

        postRepository.save(post);
        member.addPost(post);

        return postConverter.toPostDetailResponse(post);
    }

    @Transactional
    public PostDetailResponse update(Email email, Long id, PostUpdateRequest request) {
        Member member = memberService.findByEmail(email);

        Post post = member.getPosts().findPostById(id);

        post.update(
                new Title(request.getTitle()),
                new Content(request.getContent()),
                member.getId()
        );

        return postConverter.toPostDetailResponse(post);
    }


    @Transactional(readOnly = true)
    public PostDetailResponse findById(Long id) {
        return postRepository.findById(id)
                .map(postConverter::toPostDetailResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
    }

    @Transactional(readOnly = true)
    public List<PostDetailResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(postConverter::toPostDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDeleteResponse deleteByEmail(Long id, Email email) {
        Member member = memberService.findByEmail(email);
        Post post = member.getPosts().findPostById(id);
        member.getPosts().deletePost(post);

        return postConverter.toPostDeleteResponse(post.getId(), member.getEmail());
    }
}
