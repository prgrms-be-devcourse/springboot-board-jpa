package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.exception.NotFoundException;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import com.programmers.springbootboard.post.domain.Post;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
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
    private final PostRepository postRepository;

    @Transactional
    public PostCreateResponse insert(Member member, Title title, Content content) {
        Post postInstance = Post.of(title, content);
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
    public PostUpdateResponse update(Member member, Long postId, Title title, Content content) {
        Post post = member.getPosts().findPostById(postId);

        post.update(title, content);

        return toPostUpdateResponse(post);
    }

    @Transactional
    public PostDeleteResponse delete(Member member, Long postId) {
        Post post = member.getPosts().findPostById(postId);

        member.getPosts().deletePost(post);

        return toPostDeleteResponse(post);
    }
}