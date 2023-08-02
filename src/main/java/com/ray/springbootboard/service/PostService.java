 package com.ray.springbootboard.service;

import com.ray.springbootboard.domain.Post;
import com.ray.springbootboard.service.vo.PostUpdateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long save(Post post, Long userId);

    Post getPost(Long id);

    Page<Post> findAllPosts(Pageable pageable);

    Long updatePost(PostUpdateInfo info);

}
