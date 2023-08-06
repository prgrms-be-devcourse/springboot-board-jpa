package com.jpaboard.post.application;

import com.jpaboard.exception.NotFoundPostException;
import com.jpaboard.post.domain.Post;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.PostConverter;
import com.jpaboard.post.ui.dto.PostResponse;
import com.jpaboard.post.ui.dto.PostUpdateResponse;
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
    public Page<PostResponse> findPostAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertPostDto);
    }

    @Transactional(readOnly = true)
    public PostResponse findPost(long id) {
        Post post = findBydId(id);
        return PostConverter.convertPostDto(post);
    }

    public Long createPost(PostResponse postResponse) {
        Post post = postRepository.save(PostConverter.convertPost(postResponse));
        return post.getId();
    }

    public Long updatePost(long id, PostUpdateResponse postUpdateResponse) {
        Post post = findBydId(id);
        post.updatePost(postUpdateResponse.title(), postUpdateResponse.content());
        return post.getId();
    }

    private Post findBydId(long id) {
        return postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
    }
}
