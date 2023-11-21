package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostResponse createPost(String email, PostCreateRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));
        Post post = postRepository.save(request.toEntity(member));
        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long id) {
        Post post = getPostEntity(id);
        return PostResponse.from(post);
    }

    public void updatePostViewById(Long id) {
        Post post = getPostEntity(id);
        post.increaseView();
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
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));
        postRepository.deleteAllByMember(member);
    }

    private static void validateWriter(String email, Post post) {
        if (!post.isSameMember(email)) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
    }

    private Post getPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시글이 존재하지 않습니다."));
    }
}
