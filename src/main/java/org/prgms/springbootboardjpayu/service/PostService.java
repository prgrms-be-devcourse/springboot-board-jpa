package org.prgms.springbootboardjpayu.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgms.springbootboardjpayu.domain.Post;
import org.prgms.springbootboardjpayu.domain.User;
import org.prgms.springbootboardjpayu.dto.request.CreatePostRequest;
import org.prgms.springbootboardjpayu.dto.response.PostResponse;
import org.prgms.springbootboardjpayu.repository.PostRepository;
import org.prgms.springbootboardjpayu.repository.UserRepository;
import org.prgms.springbootboardjpayu.service.converter.PostConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(@Valid CreatePostRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        Post post = PostConverter.toPost(request, user);
        return PostConverter.toPostResponse(postRepository.save(post));
    }
}
