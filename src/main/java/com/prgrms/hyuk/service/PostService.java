package com.prgrms.hyuk.service;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_POST_ID_EXP_MSG;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Title;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.exception.InvalidPostIdException;
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

    public PostDto findPost(Long id) {
        return postRepository.findById(id)
            .map(converter::toPostDto)
            .orElseThrow(() -> new InvalidPostIdException(INVALID_POST_ID_EXP_MSG));
    }

    @Transactional
    public PostDto updatePost(Long id, PostUpdateRequest postUpdateRequest) {
        var post = postRepository.findById(id)
            .orElseThrow(() -> new InvalidPostIdException(INVALID_POST_ID_EXP_MSG));

        post.editTitle(new Title(postUpdateRequest.getTitle()));
        post.editContent(new Content(postUpdateRequest.getContent()));

        return converter.toPostDto(post);
    }
}
