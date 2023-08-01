package com.kdt.devcourse.module.post.application;

import com.kdt.devcourse.module.post.application.dto.PostResponse;
import com.kdt.devcourse.module.post.domain.Post;
import com.kdt.devcourse.module.post.domain.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponse getPostResponse(Long id) {
        Post post = findById(id);
        return toResponse(post);
    }

    public Long create(String title, String content) {
        Post post = new Post(title, content);
        return postRepository.save(post).getId();
    }

    public void update(Long id, String title, String content) {
        Post post = findById(id);
        post.updateTitleAndContent(title, content);
    }

    private Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
    }

    private PostResponse toResponse(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent());
    }
}
