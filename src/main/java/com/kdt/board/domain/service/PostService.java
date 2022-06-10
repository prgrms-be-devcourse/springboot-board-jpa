package com.kdt.board.domain.service;

import com.kdt.board.domain.converter.Converter;
import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.model.Post;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.global.exception.NotFoundEntityByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final Converter converter;

    @Transactional
    public Long save(PostDto.SaveRequest dto) {
        Post post = converter.convertPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }

    public PostDto.Response findById(Long id) {
        return postRepository.findById(id)
                .map(converter::convertPostDto)
                .orElseThrow(() -> new NotFoundEntityByIdException("Entity 를 찾을 수 없습니다."));
    }

    public Page<PostDto.Response> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::convertPostDto);
    }

    @Transactional
    public PostDto.Response update(PostDto.UpdateRequest dto) {
        Post post = postRepository.findById(dto.id())
                .orElseThrow(() -> new NotFoundEntityByIdException("Entity 를 찾을 수 없습니다."));
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
