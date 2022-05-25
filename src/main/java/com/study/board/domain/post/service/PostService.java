package com.study.board.domain.post.service;

import com.study.board.domain.post.domain.Post;
import com.study.board.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<Post> findAll(Pageable pageable);

    Post findById(Long postId);

    Post write(String title, String content, User writer);

    Post edit(Long postId, String title, String content, User editor);
}
