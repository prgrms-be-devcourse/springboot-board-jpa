package com.prgrms.work.post.service;

import com.prgrms.work.post.domain.Post;
import java.util.List;

public interface PostService {

    Post create(Post post);

    List<Post> getPosts();

    Post getPost(Long id);

    void update(Long id, String title, String content);

    void delete(Long id);

}
