package com.prgrms.hyuk.service;

import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.repository.PostRepository;
import com.prgrms.hyuk.service.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final Converter converter;

    public PostService(PostRepository postRepository, Converter converter) {
        this.postRepository = postRepository;
        this.converter = converter;
    }

    @Transactional
    public Long create(PostCreateRequest postCreateRequest) {
        var post = converter.toPost(postCreateRequest);
        var savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public Page<PostDto> findPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
            .map(converter::toPostDto);
    }

}
