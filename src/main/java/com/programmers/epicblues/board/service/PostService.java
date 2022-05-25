package com.programmers.epicblues.board.service;

import com.programmers.epicblues.board.dto.PostRequest;
import com.programmers.epicblues.board.dto.PostResponse;
import com.programmers.epicblues.board.entity.Post;
import com.programmers.epicblues.board.entity.User;
import com.programmers.epicblues.board.repository.JpaPostRepository;
import com.programmers.epicblues.board.repository.JpaUserRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PostService {

  private final JpaPostRepository postRepository;
  private final JpaUserRepository userRepository;

  public PostService(JpaPostRepository postRepository, JpaUserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public List<PostResponse> getPosts(PageRequest pageRequest) {

    return PostResponse.from(postRepository.findAll(pageRequest).getContent());
  }

  public PostResponse createPost(Long userId, String title, String content) {
    User user = userRepository.getById(userId);
    Post post = Post.builder().content(content).title(title).build();
    post.assignUser(user);

    return PostResponse.from(postRepository.save(post));

  }

  public PostResponse updatePost(Long postId, PostRequest postRequest) {
    var targetPost = postRepository.findById(postId).orElseThrow();
    targetPost.updateTitle(postRequest.getTitle());
    targetPost.updateContent(postRequest.getContent());
    var savedPost = postRepository.save(targetPost);
    return PostResponse.from(savedPost);
  }

  public PostResponse getPostById(long postId) {
    var queriedPost = postRepository.findById(postId).orElseThrow();
    return PostResponse.from(queriedPost);
  }
}
