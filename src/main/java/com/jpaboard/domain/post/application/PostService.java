package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.dto.request.PostCreateRequest;
import com.jpaboard.domain.post.dto.request.PostSearchRequest;
import com.jpaboard.domain.post.dto.request.PostUpdateRequest;
import com.jpaboard.domain.post.dto.response.PostPageResponse;
import com.jpaboard.domain.post.dto.response.PostResponse;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long createPost(PostCreateRequest request);

    PostResponse findPostById(Long id);

    PostPageResponse findPosts(Pageable pageable);

    PostPageResponse findPostsByCondition(PostSearchRequest request, Pageable pageable);

    void updatePost(Long id, PostUpdateRequest request);

    void deletePostById(Long id);
}
