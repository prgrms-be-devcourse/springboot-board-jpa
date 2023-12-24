package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;

import com.example.board.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.board.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse createPost(Long memberId, PostCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        Post post = postRepository.save(request.toEntity(member));
        return PostResponse.from(post);
    }

    public PostResponse findPostByIdAndUpdateView(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        post.increaseView();
        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findPostsByCondition(PostPageCondition condition) {
        Page<Post> posts = postRepository.findPostsByCondition(condition);
        return posts.map(PostResponse::from);
    }

    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findByIdWithPessimisticLock(id)
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        post.updatePost(request.title(), request.content());
        return PostResponse.from(post);
    }

    public void deletePostById(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
        postRepository.deleteById(id);
    }

    public void deletePostsByIds(List<Long> postIds) {
        postRepository.deletePostsByIds(postIds);
    }

    public void deleteAllPosts() {
        postRepository.deleteAll();
    }
}
