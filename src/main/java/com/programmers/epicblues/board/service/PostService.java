package com.programmers.epicblues.board.service;

import com.programmers.epicblues.board.dto.CreatePostRequest;
import com.programmers.epicblues.board.dto.PostResponse;
import com.programmers.epicblues.board.dto.UpdatePostRequest;
import com.programmers.epicblues.board.entity.Post;
import com.programmers.epicblues.board.entity.User;
import com.programmers.epicblues.board.repository.JpaPostRepository;
import com.programmers.epicblues.board.repository.JpaUserRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final JpaPostRepository postRepository;
  private final JpaUserRepository userRepository;

  public PostService(JpaPostRepository postRepository, JpaUserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public List<PostResponse> getPosts(Pageable pageRequest) {

    return PostResponse.from(postRepository.findAll(pageRequest).getContent());
  }

  @Transactional
  public PostResponse createPost(CreatePostRequest request) {
    User user = userRepository.findById(request.getUserId()).orElseThrow();
    Post post = Post.builder().content(request.getContent()).title(request.getTitle()).build();
    post.assignUser(user);
    return PostResponse.from(postRepository.save(post));

  }

  @Transactional
  public PostResponse updatePost(UpdatePostRequest updatePostRequest) {
    Post targetPost = postRepository.findById(updatePostRequest.getPostId()).orElseThrow();
    targetPost.updateTitle(updatePostRequest.getTitle());
    targetPost.updateContent(updatePostRequest.getContent());
    Post savedPost = postRepository.save(targetPost);
    return PostResponse.from(savedPost);
  }

  @Transactional(readOnly = true)
  public PostResponse getPostById(Long postId) {
    Post queriedPost = postRepository.findById(postId).orElseThrow();
    return PostResponse.from(queriedPost);
  }
}
