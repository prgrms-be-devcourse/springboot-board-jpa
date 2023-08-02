package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.dto.request.PostCreateRequest;
import com.jpaboard.domain.post.dto.response.PostPageResponse;
import com.jpaboard.domain.post.dto.response.PostResponse;
import com.jpaboard.domain.post.dto.request.PostUpdateRequest;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long createPost(PostCreateRequest request);

    PostResponse findPostById(Long id);

    PostPageResponse findPosts(Pageable pageable);

    PostPageResponse findPostByTitle(String title, Pageable pageable);

    PostPageResponse findPostByContent(String content, Pageable pageable);

    PostPageResponse findByKeyword(String keyword, Pageable pageable);

    void updatePost(Long id, PostUpdateRequest request);

    void deletePostById(Long id);
}
