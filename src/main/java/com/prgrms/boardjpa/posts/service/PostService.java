package com.prgrms.boardjpa.posts.service;

import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.repository.PostRepository;
import com.prgrms.boardjpa.utils.converter.PostConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.prgrms.boardjpa.utils.converter.PostConverter.postRequestToPost;
import static com.prgrms.boardjpa.utils.converter.PostConverter.postToPostDto;
import static com.prgrms.boardjpa.utils.converter.UserConverter.userDtoToUser;

@Service
public class PostService {
  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }


  //1급 컬렉션 객체를 만들어서 사용해보자
  @Transactional(readOnly = true)
  public Page<PostDto> getPosts(Pageable pageable) {
    return postRepository.findAll(pageable)
        .map(PostConverter::postToPostDto);
  }

  @Transactional(readOnly = true)
  public PostDto getPost(Long postId) {
    var post =  postRepository
        .findById(postId)
        .orElseThrow(
            () -> new NoSuchElementException("존재하지 않는 post id " + postId)
        );
    return postToPostDto(post);
  }


  public PostDto createPost(PostRequest postRequest) {
    var post = postRepository.save(
        postRequestToPost(postRequest)
    );
    return postToPostDto(post);
  }


  @Transactional
  public PostDto updatePost(Long postId, PostRequest postRequest) {
    var post = postRepository
        .findById(postId)
        .orElseThrow(
        () -> new NoSuchElementException("존재하지 않는 post id " + postId)
    );
    post.changeTitle(postRequest.getTitle());
    post.changeContent(postRequest.getContent());
    post.changeUser(userDtoToUser(
        postRequest.getUserDto())
    );
    return postToPostDto(post);
  }
}
