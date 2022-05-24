package com.sdardew.board.service;

import com.sdardew.board.dto.post.CreatePostDto;
import com.sdardew.board.domain.post.Post;
import com.sdardew.board.dto.post.PostDto;
import com.sdardew.board.dto.post.UpdatePostDto;
import com.sdardew.board.repository.PostRepository;
import com.sdardew.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public List<PostDto> getPosts() {
    return postRepository.findAll().stream().map(Post::toPostDto).collect(Collectors.toList());
  }

  public PostDto getPost(Long id) {
    Optional<Post> found = postRepository.findById(id);
    if(found.isPresent()) return found.get().toPostDto();
    throw new NoSuchElementException("존재하지 않는 PostID입니다");
  }

  @Transactional
  public PostDto createPost(CreatePostDto createPostDto) {
    Post newPost = convertToPost(createPostDto);
    postRepository.save(newPost);
    return newPost.toPostDto();
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

  @Transactional
  public PostDto updatePost(Long id, UpdatePostDto updatePostDto) {
    Optional<Post> found = postRepository.findById(id);
    if(found.isEmpty()) throw new NoSuchElementException("존재하지 않는 post 입니다");
    Post oldPost = found.get();
    oldPost.setTitle(updatePostDto.getTitle());
    oldPost.setContent(updatePostDto.getContent());
    Post save = postRepository.save(oldPost);
    return save.toPostDto();
  }
}
