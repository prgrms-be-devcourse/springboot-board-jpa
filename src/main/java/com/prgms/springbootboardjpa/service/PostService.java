package com.prgms.springbootboardjpa.service;

import com.prgms.springbootboardjpa.dto.DtoMapper;
import com.prgms.springbootboardjpa.dto.PostDto;
import com.prgms.springbootboardjpa.dto.UpdatePostRequest;
import com.prgms.springbootboardjpa.exception.PostNotFoundException;
import com.prgms.springbootboardjpa.model.Member;
import com.prgms.springbootboardjpa.model.Post;
import com.prgms.springbootboardjpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long createPost(Member member, String title, String content) {
        return postRepository.save(
                new Post(title, content, member)
        ).getPostId();
    }

    @Transactional
    public Long updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.updateTitle(updatePostRequest.getTitle());
        post.updateContent(updatePostRequest.getContent());
        return post.getPostId();
    }

    @Transactional
    public PostDto getPost(Long postId) {
        return postRepository.findById(postId)
                .map(DtoMapper::postToPostDto)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public Page<PostDto> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(DtoMapper::postToPostDto);
    }
}
