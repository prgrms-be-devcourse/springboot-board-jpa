package com.ys.board.domain.post.service;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.api.PostResponses;
import com.ys.board.domain.post.api.PostUpdateRequest;
import com.ys.board.domain.post.model.Post;
import com.ys.board.domain.post.repository.PostRepository;
import com.ys.board.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post createPost(User user, PostCreateRequest request) {
        Post post = Post.create(user, request.getTitle(), request.getContent());

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException(Post.class, postId));
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request) {
        Post post = findById(postId);

        post.updateAll(request);
    }

    @Transactional(readOnly = true)
    public PostResponses findAllByIdCursorBased(Long cursorId, Pageable pageable) {

        Slice<Post> posts = findAllByCursorIdCheckExistsCursor(cursorId, pageable);

        return new PostResponses(cursorId, posts);
    }

    private Slice<Post> findAllByCursorIdCheckExistsCursor(Long cursorId, Pageable pageable) {
        return cursorId == null ? postRepository.findAllByPageable(pageable)
            : postRepository.findAllByCursorId(cursorId, pageable);
    }

}
