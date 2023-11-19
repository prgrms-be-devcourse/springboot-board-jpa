package com.example.board.domain.post.service;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.dto.PostCreateRequest;
import com.example.board.domain.post.dto.PostResponse;
import com.example.board.domain.post.dto.PostUpdateRequest;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostResponse createPost(String email, PostCreateRequest request) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        Post post = postRepository.save(request.toEntity(member));
        return PostResponse.from(post);
    }

    public PostResponse findPostById(Long id) {
        Post post = getPostEntity(id);
        post.increaseView();
        return PostResponse.from(post);
    }

    public List<PostResponse> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostResponse::from).toList();
    }

    @Transactional
    public PostResponse updatePost(Long id, String email, PostUpdateRequest request) {
        Post post = getPostEntity(id);
        if (!post.isSameMember(email)) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        post.updatePost(request.title(), request.content());
        return PostResponse.from(post);
    }

    @Transactional
    public void deletePostById(Long id, String email) {
        Post post = getPostEntity(id);
        if (!post.isSameMember(email)) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    @Transactional
    public void deleteAllPostsByWriter(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        postRepository.deleteAllByMember(member);
    }

    private Post getPostEntity(Long id) {
        return postRepository.findById(id).orElseThrow();
    }
}
