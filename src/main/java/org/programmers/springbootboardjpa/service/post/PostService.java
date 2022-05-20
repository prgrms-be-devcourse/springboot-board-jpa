package org.programmers.springbootboardjpa.service.post;

import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.web.dto.post.PostCreateForm;
import org.programmers.springbootboardjpa.web.dto.post.PostUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Long writePost(PostCreateForm postCreateForm);

    Post findPostWithPostId(Long userId);

    Post editPost(PostUpdateForm postUpdateForm);

    void deletePost(Long postId);

    Page<Post> findPostsWithPage(Pageable pageable);

    Page<Post> findPostByUserWithPage(Long userId, Pageable pageable);
}
