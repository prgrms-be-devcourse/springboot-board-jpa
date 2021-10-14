package com.board.springbootboard.service;

import com.board.springbootboard.domain.posts.PostsRepository;
import com.board.springbootboard.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;  // 생성자를 통한 주입

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }



}
