package com.kdt.postproject.post.service;

import com.kdt.postproject.domain.post.Post;
import com.kdt.postproject.domain.post.PostRepository;
import com.kdt.postproject.post.converter.PostConverter;
import com.kdt.postproject.post.dto.PostDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private PostConverter postConverter;

    public PostService(PostRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        //1. dto -> entity 변환(준영속)
        Post post = postConverter.convertPost(postDto);
        //2. orderRepository.save(entity) -> 영속화
        Post entity = postRepository.save(post);
        //3. 결과반환
        return entity.getId();
    }

    @Transactional
    public PostDto update(PostDto postDto) throws NotFoundException{

        Optional<Post> post = postRepository.findById(postDto.getId());
        if(post.isEmpty()) {
            throw new NotFoundException("해당 글을 찾을 수 없습니다");
        }
        post.get().setTitle(postDto.getTitle());
        post.get().setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post.get());
        return postConverter.convertPostDto(updatedPost);
    }

    @Transactional
    public PostDto findOne(Long id) throws NotFoundException {
        // 1. 조회를 위한 키값 인자로 받기
        // 2. orderRepository.findById(uuid) -> 조회 (영속화된 엔티티)
        // 3. entity -> dto
        return postRepository.findById(id)
                .map(postConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundException("해당 글을 찾을 수 없습니다"));
    }

    @Transactional
    public Page<PostDto> findAll(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(postConverter::convertPostDto);
    }
}
