package com.kdt.devcourse.module.post.application;

import com.kdt.devcourse.module.post.domain.Post;
import com.kdt.devcourse.module.post.domain.repository.PostRepository;
import com.kdt.devcourse.module.post.presentation.dto.CreateOrUpdatePost;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.devcourse.global.exception.ErrorCode.POST_NOT_FOUND;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<CreateOrUpdatePost.Response> findAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public CreateOrUpdatePost.Response getPostResponse(Long id) {
        Post post = findById(id);
        return toResponse(post);
    }

    @Transactional
    public void create(String title, String content) {
        Post post = new Post(title, content);
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        Post post = findById(id);
        post.updateTitleAndContent(title, content);
    }

    private Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND.getMessage()));
    }

    private CreateOrUpdatePost.Response toResponse(Post post) {
        return new CreateOrUpdatePost.Response(post.getId(), post.getTitle(), post.getContent());
    }
}
