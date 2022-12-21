package com.ys.board.domain.post.service;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.post.model.Post;
import com.ys.board.domain.post.api.PostUpdateRequest;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.api.PostResponse;
import com.ys.board.domain.post.api.PostResponses;
import com.ys.board.domain.post.repository.PostRepository;
import com.ys.board.domain.user.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PostResponses findAllByIdCursorBased(Long cursorId, int pageSize) {

        Pageable pageable = PageRequest.of(0, pageSize + 1);

        List<Post> posts = findAllByCursorIdCheckExistsCursor(cursorId, pageable);

        boolean hasNext = hasNext(posts.size(), pageSize);

        return new PostResponses(
            toSubListIfHasNext(hasNext, pageSize, posts).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList()),
            cursorId, hasNext);
    }

    private List<Post> findAllByCursorIdCheckExistsCursor(Long cursorId, Pageable pageable) {
        return cursorId == null ? postRepository.findAllByOrderByIdDescCreatedAtDesc(pageable)
            : postRepository.findByIdLessThanOrderByIdDescCreatedAtDesc(cursorId, pageable);
    }

    private boolean hasNext(int postsSize, int pageSize) {
        if (postsSize == 0) {
            throw new EntityNotFoundException(Post.class);
        }

        return postsSize > pageSize;
    }

    private List<Post> toSubListIfHasNext(boolean hasNext, int pageSize, List<Post> posts) {
        return hasNext ? posts.subList(0, pageSize) : posts;
    }

}
