package com.kdt.board.domain.service;

import com.kdt.board.domain.converter.Converter;
import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.model.Post;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.global.exception.LoadEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Converter converter;

    @Transactional
    public Long save(PostDto.Save dto) {
        Post post = converter.convertPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    @Transactional
    public PostDto.Response findById(Long id) {
        return postRepository.findById(id)
                .map(converter::convertPostDto)
                .orElseThrow(() -> new LoadEntityException("Entity 를 불러오는 중 예외 발생"));
    }

    @Transactional
    public Page<PostDto.Response> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::convertPostDto);
    }

    @Transactional
    public PostDto.Response update(PostDto.Update dto) {
        Post post = postRepository.findById(dto.id())
                .orElseThrow(() -> new LoadEntityException("Entity 를 불러오는 중 예외 발생"));
        post.update(dto.title(), dto.content());
        return converter.convertPostDto(post);
    }

    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public long count() {
        return postRepository.count();
    }
}
