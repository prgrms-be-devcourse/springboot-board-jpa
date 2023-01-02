package com.programmers.kwonjoosung.board.service;

import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.model.dto.PostInfo;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostInfo findPostByPostId(Long postId);

    List<PostInfo> findPostByUserId(Long userId);

    List<PostInfo> findAllPost();

    Page<PostInfo> findAllPost(Pageable pageable);

    IdResponse createPost(Long userId, CreatePostRequest request);

    PostInfo updatePost(Long postId, UpdatePostRequest request);
}
