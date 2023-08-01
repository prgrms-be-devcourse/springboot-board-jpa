package com.programmers.jpaboard.service;

import com.programmers.jpaboard.domain.Post;
import com.programmers.jpaboard.domain.User;
import com.programmers.jpaboard.dto.request.PostCreateRequest;
import com.programmers.jpaboard.dto.response.PostCreateResponse;
import com.programmers.jpaboard.repository.PostRepository;
import com.programmers.jpaboard.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostCreateResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoSuchElementException("올바르지 않은 사용자의 ID입니다."));

        Post post = request.toEntity();
        post.updateUser(user);

        Post savedPost = postRepository.save(post);

        return PostCreateResponse.of(savedPost.getId());
    }
}
