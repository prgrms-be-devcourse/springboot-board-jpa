package com.sdardew.board.service;

import com.sdardew.board.domain.post.CreatePostDto;
import com.sdardew.board.domain.post.Post;
import com.sdardew.board.repository.PostRepository;
import com.sdardew.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public List<Post> getPosts() {
    return postRepository.findAll();
  }

  public Post getPost(Long id) {
    Optional<Post> found = postRepository.findById(id);
    if(found.isPresent()) return found.get();
    throw new NoSuchElementException("존재하지 않는 PostID입니다");
  }

  @Transactional
  public Post createPost(CreatePostDto createPostDto) {
    Post newPost = convertToPost(createPostDto);
    postRepository.save(newPost);
    return newPost;
  }

  public void deleteAll() {
    postRepository.deleteAll();
  }

  private Post convertToPost(CreatePostDto createPostDto) {
    Post newPost = new Post();
    newPost.setTitle(createPostDto.getTitle());
    newPost.setContent(createPostDto.getContent());
    newPost.setCreateAt(LocalDateTime.now());
    newPost.setUser(userRepository.getById(createPostDto.getUserId()));
    return newPost;
  }
}
