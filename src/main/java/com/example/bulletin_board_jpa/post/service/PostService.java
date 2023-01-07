package com.example.bulletin_board_jpa.post.service;

import com.example.bulletin_board_jpa.post.Post;
import com.example.bulletin_board_jpa.post.PostRepository;
import com.example.bulletin_board_jpa.post.converter.PostConverter;
import com.example.bulletin_board_jpa.post.dto.PostRequestDto;
import com.example.bulletin_board_jpa.post.dto.PostResponseDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    // 영속성 컨텍스트를 관리하는 entity manager 를 만들 필요 없음
    // tx.begin, tx.commit 필요 없음
    @Transactional
    public Long save(PostRequestDto postRequestDto) {
        // 1. dto -> entity 변환 (준영속)
        Post post = postConverter.convertToPost(postRequestDto);
        // 2. postRepository.save(entity) (영속)
        Post entity = postRepository.save(post);
        // 3. 결과 반환
        return entity.getId();
    }

    // 영속화딘 엔티티를 관리하기 위해서는 entityManager 관리 영역으로 묶어줘야 한다 -> @Transactional
    @Transactional
    public PostResponseDto findOne(Long postId) throws ChangeSetPersister.NotFoundException {
        // 1. 조회를 위한 키값 인자로 받기
        // 2. postRepository.findById(postId) -> 조회(영속화된 엔티티)
        // 3. entity -> dto , 영속화 된 객체가 빠져나가서 의도치 않은 쿼리 나가는 것을 방지
        return postRepository.findById(postId)
                .map(postConverter::convertToPostResponseDto)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @Transactional
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertToPostResponseDto);
    }
}
