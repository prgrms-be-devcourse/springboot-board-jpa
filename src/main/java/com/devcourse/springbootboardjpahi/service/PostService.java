package com.devcourse.springbootboardjpahi.service;

import com.devcourse.springbootboardjpahi.domain.Post;
import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PostDetailResponse;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.repository.PostRepository;
import com.devcourse.springbootboardjpahi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse create(CreatePostRequest request) {
        User author = userRepository.findById(request.userId())
                .orElseThrow();
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(author)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponse.from(savedPost);
    }

    public PostDetailResponse findById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow();

        return PostDetailResponse.from(post);
    }
}
