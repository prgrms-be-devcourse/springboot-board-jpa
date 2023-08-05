package com.jpaboard.post.application;

import com.jpaboard.exception.NotFoundPostException;
import com.jpaboard.post.domain.Post;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.PostConverter;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.post.ui.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PostService {

    private final JpaPostRepository postRepository;

    public PostService(JpaPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findPostAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertPostDto);
    }

    @Transactional(readOnly = true)
    public PostDto findPost(long id) {
        return postRepository.findById(id)
                .map(PostConverter::convertPostDto)
                .orElseThrow(NotFoundPostException::new);
    }

    public Long createPost(PostDto postDto) {
        Post post = postRepository.save(PostConverter.convertPost(postDto));
        return post.getId();
    }

    public Long updatePost(long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
        post.updatePost(postUpdateDto.title(), postUpdateDto.content());

        return post.getId();
    }
}
