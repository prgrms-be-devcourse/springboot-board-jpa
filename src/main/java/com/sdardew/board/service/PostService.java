package com.sdardew.board.service;

import com.sdardew.board.dto.post.CreatePostDto;
import com.sdardew.board.domain.post.Post;
import com.sdardew.board.dto.post.DetailedPostDto;
import com.sdardew.board.dto.post.PostDto;
import com.sdardew.board.dto.post.UpdatePostDto;
import com.sdardew.board.repository.PostRepository;
import com.sdardew.board.repository.UserRepository;
import org.springframework.data.domain.Pageable;
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
  private final DtoConverter dtoConverter;

  public PostService(PostRepository postRepository, UserRepository userRepository, DtoConverter dtoConverter) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.dtoConverter = dtoConverter;
  }

  public List<PostDto> getPosts() {
    return postRepository.findAll().stream().map(Post::toPostDto).collect(Collectors.toList());
  }

  public DetailedPostDto getPost(Long id) {
    Optional<Post> found = postRepository.findById(id);
    if(found.isPresent()) {
      return dtoConverter.toDetailedPostDto(found.get());
    }
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
    Post newPost = new Post(createPostDto.getTitle(), createPostDto.getContent(), LocalDateTime.now(), userRepository.getById(createPostDto.getUserId()));
    return newPost;
  }

  @Transactional
  public PostDto updatePost(Long id, UpdatePostDto updatePostDto) {
    Optional<Post> found = postRepository.findById(id);
    if(found.isEmpty()) throw new NoSuchElementException("존재하지 않는 post 입니다");
    Post oldPost = found.get();
    oldPost.updatePost(updatePostDto.getTitle(), updatePostDto.getContent());
    Post save = postRepository.save(oldPost);
    return save.toPostDto();
  }

  public List<PostDto> getPosts(Pageable pageable) {
    return postRepository.findAll(pageable).stream().map(Post::toPostDto).collect(Collectors.toList());
  }
}
