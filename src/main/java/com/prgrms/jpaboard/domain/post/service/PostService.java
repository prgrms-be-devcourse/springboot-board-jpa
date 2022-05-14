package com.prgrms.jpaboard.domain.post.service;

import com.prgrms.jpaboard.domain.post.domain.Post;
import com.prgrms.jpaboard.domain.post.domain.PostRepository;
import com.prgrms.jpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.exception.UserNotFoundException;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ResultDto createPost(PostRequestDto postRequestDto) {
        userRepository.findById(postRequestDto.getUserId()).orElseThrow(() -> new UserNotFoundException());

        Post savedPost = postRepository.save(postRequestDto.toEntity());

        return ResultDto.createResult(savedPost.getId());
    }
}
