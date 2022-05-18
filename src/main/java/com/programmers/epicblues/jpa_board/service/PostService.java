package com.programmers.epicblues.jpa_board.service;

import com.programmers.epicblues.jpa_board.dto.PostResponse;
import com.programmers.epicblues.jpa_board.entity.Post;
import com.programmers.epicblues.jpa_board.entity.User;
import com.programmers.epicblues.jpa_board.repository.JpaPostRepository;
import com.programmers.epicblues.jpa_board.repository.JpaUserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PostService {

  private final JpaPostRepository postRepository;
  private final JpaUserRepository userRepository;

  public PostService(JpaPostRepository postRepository, JpaUserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public List<PostResponse> getPosts(PageRequest pageRequest) {

    return postRepository.findAll(pageRequest).getContent().stream().map(PostResponse::from)
        .collect(Collectors.toUnmodifiableList());
  }

  public List<PostResponse> getPosts() {
    return postRepository.findAll(Sort.by("createdAt").descending()).stream()
        .map(PostResponse::from).collect(Collectors.toList());
  }

  public void createPost(Long userId, String title, String content) {
    User user = userRepository.findById(userId).orElseThrow();
    Post post = Post.builder().content(content).title(title).build();
    post.assignUser(user);

    postRepository.save(post);

  }

  public PostResponse updatePost(long postId, String title, String content) {
    var targetPost = postRepository.findById(postId).orElseThrow();
    targetPost.updateTitle(title);
    targetPost.updateContent(content);
    var savedPost = postRepository.save(targetPost);
    return PostResponse.from(savedPost);
  }

  public PostResponse getPostById(long postId) {
    var queriedPost = postRepository.findById(postId).orElseThrow();
    return PostResponse.from(queriedPost);
  }
}
