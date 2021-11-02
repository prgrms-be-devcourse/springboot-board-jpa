package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.exception.NotFoundException;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.dto.PostBundle;
import com.programmers.springbootboard.post.dto.PostResponse;
import com.programmers.springbootboard.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.programmers.springbootboard.post.dto.PostResponse.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponse insert(PostBundle bundle) {
        Member member = memberRepository.findByEmail(bundle.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });

        Post postInstance = Post.of(bundle.getTitle(), bundle.getContent());
        postInstance.addPost(member);

        Post postEntity = postRepository.save(postInstance);
        return PostResponse.toPostCreateResponse(postEntity);
    }


    @Transactional(readOnly = true)
    public PostReadResponse get(PostBundle bundle) {
        return postRepository.findById(bundle.getPostId())
                .map(PostResponse::toPostReadResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_POST);
                });
    }

    @Transactional(readOnly = true)
    public Page<PostReadAllResponse> getAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponse::toPostReadAllResponse);
    }

    @Transactional
    public PostUpdateResponse update(PostBundle bundle) {
        Member member = memberRepository.findByEmail(bundle.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });

        Post post = member.getPosts().findPostById(bundle.getPostId());

        post.update(bundle.getTitle(), bundle.getContent());

        return toPostUpdateResponse(post);
    }

    @Transactional
    public PostDeleteResponse delete(PostBundle bundle) {
        Member member = memberRepository.findByEmail(bundle.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        Post post = member.getPosts().findPostById(bundle.getPostId());
        member.getPosts().deletePost(post);

        return toPostDeleteResponse(post);
    }
}
