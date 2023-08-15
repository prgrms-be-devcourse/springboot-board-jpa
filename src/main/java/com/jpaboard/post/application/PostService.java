package com.jpaboard.post.application;

import com.jpaboard.exception.NotFoundPostException;
import com.jpaboard.post.domain.Post;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.PostConverter;
import com.jpaboard.post.ui.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class PostService {

    private final JpaPostRepository postRepository;

    public PostService(JpaPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostDto.Response> findPostAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostConverter::convertPostResponse);
    }

    public PostDto.Response findPost(long id) {
        Post post = findBydId(id);
        return PostConverter.convertPostResponse(post);
    }

    @Transactional
    public Long createPost(PostDto.Request request) {
        Post post = postRepository.save(PostConverter.convertPostRequest(request));
        return post.getId();
    }

    @Transactional
    public Long updatePost(long id, PostDto.PostUpdateRequest postUpdateRequest) {
        Post post = findBydId(id);
        post.updatePost(postUpdateRequest.title(), postUpdateRequest.content());
        return post.getId();
    }

    private Post findBydId(long id) {
        return postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
    }
}
