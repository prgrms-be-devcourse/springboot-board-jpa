package com.hyunji.jpaboard.domain.post.service;

import com.hyunji.jpaboard.domain.post.domain.Post;
import org.springframework.data.domain.Page;

public interface PostService {

    void register(Post post);

    Page<Post> findPage(int pageNum);

    Post findById(Long id);
}
