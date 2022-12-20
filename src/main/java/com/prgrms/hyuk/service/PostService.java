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
import java.util.List;
import java.util.stream.Collectors;
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

    public List<PostDto> findPosts(int offset, int limit) {
        return postRepository.findAll(offset, limit).stream()
            .map(converter::toPostDto)
            .collect(Collectors.toList());
    }

    public PostDto findPost(Long id) {
        return postRepository.findById(id)
            .map(converter::toPostDto)
            .orElseThrow(() -> new InvalidPostIdException(INVALID_POST_ID_EXP_MSG));
    }

    @Transactional
    public Long updatePost(Long id, PostUpdateRequest postUpdateRequest) {
        var post = postRepository.findById(id)
            .orElseThrow(() -> new InvalidPostIdException(INVALID_POST_ID_EXP_MSG));

        post.editTitleAndContent(
            new Title(postUpdateRequest.getTitle()),
            new Content(postUpdateRequest.getContent())
        );

        return post.getId();
    }
}
