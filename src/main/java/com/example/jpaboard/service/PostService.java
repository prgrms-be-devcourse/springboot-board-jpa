package com.example.jpaboard.service;

import com.example.jpaboard.converter.PostConverter;
import com.example.jpaboard.domain.Post;
import com.example.jpaboard.domain.User;
import com.example.jpaboard.dto.PostRequest;
import com.example.jpaboard.dto.PostResponse;
import com.example.jpaboard.exception.PostNotFoundException;
import com.example.jpaboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createPost(User user, PostRequest postRequest) {
        Post post = new Post(postRequest.getTitle(), postRequest.getContent());
        post.setAuthor(user);
        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
    }

    public Post update(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return post.update(postRequest);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {

        return postRepository.findAll(pageable).map(PostConverter::convertPostResponse);
    }
}
