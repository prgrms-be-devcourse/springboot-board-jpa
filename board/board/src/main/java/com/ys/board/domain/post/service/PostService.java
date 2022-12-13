package com.ys.board.domain.post.service;

import com.ys.board.domain.post.Post;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostCreateRequest request) {
        Post post = Post.create(request.getTitle(), request.getContent());

        return postRepository.save(post);
    }

}
