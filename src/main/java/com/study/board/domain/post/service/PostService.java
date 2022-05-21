package com.study.board.domain.post.service;

import com.study.board.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface PostService {

    @Transactional(readOnly = true)
    Page<Post> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Post findById(Long postId);

    Post write(String title, String content, Long writerId);

    Post edit(Long postId, String title, String content, Long editorId);
}
