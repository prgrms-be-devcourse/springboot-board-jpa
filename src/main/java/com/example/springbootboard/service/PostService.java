package com.example.springbootboard.service;

import com.example.springbootboard.converter.PostConverter;
import com.example.springbootboard.domain.Post;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository, UserRepository userRepository, PostConverter postConverter){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto dto) {
        // 1. dto -> entity 변환 (준영속)
        Post post = postConverter.convertPost(dto);
        post.setCreatedBy(userRepository.findById(dto.getCreated_by()).get());
        // 2. orderRepository.save(enitiy) -> 영속화
        Post entity = postRepository.save(post);
        // 3. 결과 반환
        return entity.getId();
    }

    @Transactional
    public PostDto findOne(Long id) throws NotFoundException {
        // 1. 조회를 위한 키값 인자로 받기
        // 2. orderRepository.findById(uuid) -> 조회 (영속화된 엔티티)
        return postRepository.findById(id)
                .map(postConverter::convertPostDto) // 3. entity -> dto
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }
}