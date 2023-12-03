package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.board.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse createPost(String email, PostCreateRequest request) {
        Member member = getMemberEntity(email);
        Post post = postRepository.save(request.toEntity(member));
        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long id) {
        Post post = getPostEntity(id);
        return PostResponse.from(post);
    }

    public void updatePostViewById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new BusinessException(POST_NOT_FOUND);
        }
        postRepository.updateView(id);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findPostsByCondition(PostPageCondition condition) {
        Page<Post> posts = postRepository.findPostsByCondition(condition);
        return posts.map(PostResponse::from);
    }

    public PostResponse updatePost(Long id, String email, PostUpdateRequest request) {
        Post post = getPostEntity(id);
        validateWriter(email, post);
        post.updatePost(request.title(), request.content());
        return PostResponse.from(post);
    }

    public void deletePostById(Long id, String email) {
        Post post = getPostEntity(id);
        validateWriter(email, post);
        postRepository.deleteById(id);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    public void deleteAllPostsByWriter(String email) {
        Member member = getMemberEntity(email);
        postRepository.deleteAllByMember(member);
    }

    private void validateWriter(String email, Post post) {
        getMemberEntity(email);
        if (!post.isSameMember(email)) {
            throw new BusinessException(WRITER_MISMATCH);
        }
    }

    private Post getPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
    }

    private Member getMemberEntity(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }
}
