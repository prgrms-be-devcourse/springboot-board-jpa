package com.prgrms.work.post.service;

import com.prgrms.work.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Post create(Post post);

    Page<Post> getPosts(Pageable pageable);

    Post getPost(Long id);

    void update(Long id, String title, String content);

    void delete(Long id);

}
