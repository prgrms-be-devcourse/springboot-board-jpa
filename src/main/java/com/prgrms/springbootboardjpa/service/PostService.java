package com.prgrms.springbootboardjpa.service;

import com.prgrms.springbootboardjpa.dto.PostRequest;
import com.prgrms.springbootboardjpa.dto.PostResponse;
import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.exception.NotFoundException;
import com.prgrms.springbootboardjpa.repository.PostRepository;
import com.prgrms.springbootboardjpa.dto.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    private final DtoMapper dtoConverter;

    public PostService(PostRepository postRepository, DtoMapper dtoConverter) {
        this.postRepository = postRepository;
        this.dtoConverter = dtoConverter;
    }

    @Transactional
    public PostResponse save(PostRequest postRequest) {
        Post post = dtoConverter.convertPost(postRequest);

        Post entity = postRepository.save(post);

        return dtoConverter.convertPost(entity);
    }

    @Transactional(readOnly = true)
    public PostResponse getOne(long id) {
        return postRepository.findById(id)
                .map(dtoConverter::convertPost)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(dtoConverter::convertPost);
    }

    @Transactional
    public PostResponse update(long id, PostRequest postRequest) {
        return dtoConverter.convertPost(
                postRepository.findById(id)
                        .map(post -> {
                            if (postRequest.getTitle() != null) post.setTitle(postRequest.getTitle());
                            if (postRequest.getContent() != null) post.setContent(postRequest.getContent());
                            return postRepository.save(post);
                        })
                        .orElseThrow(NotFoundException::new));
    }

    @Transactional
    public void delete(long id) {
        try {
            postRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Cannot found user for {}", id);
        }
    }
}
