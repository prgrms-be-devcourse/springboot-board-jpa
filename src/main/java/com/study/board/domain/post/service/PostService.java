package com.study.board.domain.post.service;

import com.study.board.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Page<Post> findAll(Pageable pageable);

    Post findById(Long postId);

    Post write(String title, String content, String writerName);

    Post edit(Long postId, String title, String content, String editorName);
}
