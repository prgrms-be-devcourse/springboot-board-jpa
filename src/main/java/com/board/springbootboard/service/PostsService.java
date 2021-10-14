package com.board.springbootboard.service;

import com.board.springbootboard.domain.posts.Posts;
import com.board.springbootboard.domain.posts.PostsRepository;
import com.board.springbootboard.web.dto.PostsResponseDto;
import com.board.springbootboard.web.dto.PostsSaveRequestDto;
import com.board.springbootboard.web.dto.PostsUpdateRequestsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;  // 생성자를 통한 주입

    // 저장
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 수정 - Posts Entity의 dirtyChecking으로 별다른 명령어 필요 없음. entity만 수정하면 됨.
    @Transactional
    public Long update(Long id, PostsUpdateRequestsDto requestsDto) {
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        posts.update(requestsDto.getTitle(),requestsDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }



}
