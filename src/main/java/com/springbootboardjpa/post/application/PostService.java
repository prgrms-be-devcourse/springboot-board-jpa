package com.springbootboardjpa.post.application;

import com.springbootboardjpa.member.domain.Member;
import com.springbootboardjpa.member.domain.MemberRepository;
import com.springbootboardjpa.post.domain.Content;
import com.springbootboardjpa.post.domain.Post;
import com.springbootboardjpa.post.domain.PostRepository;
import com.springbootboardjpa.post.dto.PostRequest;
import com.springbootboardjpa.post.dto.PostResponse;
import com.springbootboardjpa.post.dto.PostWithoutContentResponse;
import com.springbootboardjpa.post.dto.PostsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public PostResponse save(PostRequest request) {
        Content content = new Content(request.content());
        Member member = memberRepository.getById(request.memberId());

        Post post = new Post(member, content, request.title());
        postRepository.save(post);
        return new PostResponse(post);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.getById(id);
        return new PostResponse(post);
    }

    public PostsResponse findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostWithoutContentResponse> result = posts.stream()
                .map(PostWithoutContentResponse::new)
                .toList();
        return new PostsResponse(result);
    }

    @Transactional
    public void updateById(Long id, PostRequest request) {
        Post post = postRepository.getById(id);
        post.changeTitle(request.title());
        post.changeContent(request.content());
    }
}
