package com.prgrms.springbootboardjpa.service;

import com.prgrms.springbootboardjpa.dto.PostDtoMapper;
import com.prgrms.springbootboardjpa.dto.PostRequest;
import com.prgrms.springbootboardjpa.dto.PostResponse;
import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.exception.NotFoundException;
import com.prgrms.springbootboardjpa.repository.PostRepository;
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

    private final PostDtoMapper postDtoMapper;

    public PostService(PostRepository postRepository, PostDtoMapper postDtoMapper) {
        this.postRepository = postRepository;
        this.postDtoMapper = postDtoMapper;
    }

    @Transactional
    public PostResponse save(PostRequest postRequest) {
        Post post = postDtoMapper.convertPost(postRequest);
        // 임시 처리 (AuditAware 컴포넌트 추가 예정)
        post.setCreatedBy(postDtoMapper.extractName(postRequest.getUser()));

        Post entity = postRepository.save(post);

        return postDtoMapper.convertPost(entity);
    }

    @Transactional(readOnly = true)
    public PostResponse getOne(long id) {
        return postRepository.findById(id)
                .map(postDtoMapper::convertPost)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postDtoMapper::convertPost);
    }

    @Transactional
    public PostResponse update(long id, PostRequest postRequest) {
        return postDtoMapper.convertPost(
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
