package com.example.jpaboard.post.application;

import com.example.jpaboard.post.converter.PostConverter;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.dto.PostDto;
import com.example.jpaboard.user.domain.User;
import com.example.jpaboard.post.dto.PostRequest;
import com.example.jpaboard.post.dto.PostResponse;
import com.example.jpaboard.exception.PostNotFoundException;
import com.example.jpaboard.post.infra.PostRepository;
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

    public PostDto getPost(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return PostConverter.convertPostDto(findPost);
    }

    public Post update(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return post.update(postRequest);
    }

    public Page<PostResponse> getPosts(Pageable pageable) {

        return postRepository.findAll(pageable).map(PostConverter::convertPostResponse);
    }
}
