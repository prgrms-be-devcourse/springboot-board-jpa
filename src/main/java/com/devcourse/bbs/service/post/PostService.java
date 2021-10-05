package com.devcourse.bbs.service.post;

import com.devcourse.bbs.controller.bind.PostCreateRequest;
import com.devcourse.bbs.controller.bind.PostUpdateRequest;
import com.devcourse.bbs.domain.post.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO createPost(PostCreateRequest request);
    PostDTO updatePost(PostUpdateRequest request);
    Optional<PostDTO> findPostById(long id);
    List<PostDTO> findPostsByPage(int pageNum, int pageSize);
}
