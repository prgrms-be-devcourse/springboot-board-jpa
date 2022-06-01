package com.hyunji.jpaboard.domain.post.service;

import com.hyunji.jpaboard.domain.post.domain.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    void save(Post post);

    Page<Post> findPage(int pageNum);

    Post findPostByIdWithUser(Long id);
}
