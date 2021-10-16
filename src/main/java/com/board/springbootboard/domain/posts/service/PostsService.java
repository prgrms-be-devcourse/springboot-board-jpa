package com.board.springbootboard.domain.posts.service;

import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.posts.PostsRepository;
import com.board.springbootboard.domain.posts.dto.PostsListResponseDto;
import com.board.springbootboard.domain.posts.dto.PostsResponseDto;
import com.board.springbootboard.domain.posts.dto.PostsSaveRequestDto;
import com.board.springbootboard.domain.posts.dto.PostsUpdateRequestsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        PostsEntity posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        posts.update(requestsDto.getTitle(),requestsDto.getContent());
        return id;
    }

    // id로 단건 조회
    @Transactional
    public PostsResponseDto findById(Long id) {
        PostsEntity entity=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return new PostsResponseDto(entity);
    }

    // 다건 조회 (id 별 내림차순)
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // Map을 통해
                .collect(Collectors.toList()); // List로 변환
    }



}
