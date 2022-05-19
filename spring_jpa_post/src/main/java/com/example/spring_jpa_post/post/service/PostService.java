package com.example.spring_jpa_post.post.service;

import com.example.spring_jpa_post.post.dto.request.CreatePostRequest;
import com.example.spring_jpa_post.post.dto.response.FoundPostResponse;
import com.example.spring_jpa_post.post.dto.request.ModifyPostRequest;
import com.example.spring_jpa_post.post.repository.PostRepository;
import com.example.spring_jpa_post.post.entity.Post;
import com.example.spring_jpa_post.post.exception.PostNotFoundException;
import com.example.spring_jpa_post.user.exception.UserNotFoundException;
import com.example.spring_jpa_post.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(CreatePostRequest createPostRequest) {
        Long userId = createPostRequest.getUserId();
        Post post = postMapper.toPostFromCreatePostRequest(createPostRequest);
        post.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
        return postRepository.save(post).getId();
    }

    public FoundPostResponse getPostById(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::toFoundResponseFromPost)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Page<FoundPostResponse> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postMapper::toFoundResponseFromPost);
    }

    @Transactional
    public Long modifyPost(Long postId, ModifyPostRequest modifyPostRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
        post.changContent(modifyPostRequest.getContent());
        post.changeTitle(modifyPostRequest.getTitle());
        return post.getId();
    }
}
