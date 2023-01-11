package com.example.springbootboardjpa.domain.post.service;

import com.example.springbootboardjpa.domain.member.repository.MemberRepository;
import com.example.springbootboardjpa.domain.post.entity.Post;
import com.example.springbootboardjpa.domain.post.repository.PostRepository;
import com.example.springbootboardjpa.global.exception.CustomException;
import com.example.springbootboardjpa.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.springbootboardjpa.domain.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Post save(String title, String content, long memberId) {
        Member member = getMember(memberId);

        Post post = new Post(title, content);
        post.setMember(member);

        return postRepository.save(post);
    }

    public Page<Post> findAll(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest);
    }

    public Post findById(long id) {
        return getPost(id);
    }

    @Transactional
    public Post update(long id, String title, String content, long memberId) {
        Post post = getPost(id);
        idMatching(post.getMember().getId(), memberId);
        post.update(title, content);
        return post;
    }

    private Post getPost(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private Member getMember(long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private void idMatching(long id1, long id2) {
        if (id1 != id2) {
            throw new CustomException(ErrorCode.NOT_HAVA_PERMISSION);
        }
    }
}
