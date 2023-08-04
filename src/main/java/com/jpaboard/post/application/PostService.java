package com.jpaboard.post.application;

import com.jpaboard.exception.NotFoundPostException;
import com.jpaboard.post.domain.Post;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.PostConverter;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.post.ui.dto.PostUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PostService {

    private final static String NOT_FOUND_POST_MESSAGE = "게시글 정보를 찾을 수 없습니다.";
    private final JpaPostRepository postRepository;

    public PostService(JpaPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostDto> findPostAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertPostDto);
    }

    public PostDto findPost(long id) {
        return postRepository.findById(id)
                .map(PostConverter::convertPostDto)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST_MESSAGE));
    }

    public Long createPost(PostDto postDto) {
        Post post = postRepository.save(PostConverter.convertPost(postDto));
        return post.getId();
    }

    public Long updatePost(long id, PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST_MESSAGE));
        post.updatePost(postUpdateDto.title(), postUpdateDto.content());

        return post.getId();
    }
}
