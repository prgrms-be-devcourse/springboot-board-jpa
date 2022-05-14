package com.prgrms.jpaboard.domain.post.service;

import com.prgrms.jpaboard.domain.post.domain.Post;
import com.prgrms.jpaboard.domain.post.domain.PostRepository;
import com.prgrms.jpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.jpaboard.domain.user.domain.User;
import com.prgrms.jpaboard.domain.user.domain.UserRepository;
import com.prgrms.jpaboard.domain.user.exception.UserNotFoundException;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResultDto createPost(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId()).orElseThrow(() -> new UserNotFoundException());

        Post post = postRequestDto.toEntity();
        post.setUser(user);

        Post savedPost = postRepository.save(post);

        return ResultDto.createResult(savedPost.getId());
    }
}
