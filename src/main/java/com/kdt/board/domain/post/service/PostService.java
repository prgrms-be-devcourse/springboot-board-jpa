package com.kdt.board.domain.post.service;

import com.kdt.board.domain.post.dto.PostRequestDto;
import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.post.repository.PostRepository;
import com.kdt.board.domain.user.entity.User;
import com.kdt.board.domain.user.repository.UserRepository;
import com.kdt.board.global.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post save(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new BaseException("등록되지 않은 유저입니다"));
        Post post = PostRequestDto.from(postRequestDto, user);
        return postRepository.save(post);
    }
}
