package com.prgrms.boardjpa.posts.service;

import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.repository.PostRepository;
import com.prgrms.boardjpa.utils.converter.PostConverter;
import com.prgrms.boardjpa.utils.exception.NoSuchPostException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            () -> new NoSuchPostException("존재하지 않는 post id 입니다.")
        );
    return PostConverter.postToPostDto(post);
  }


  public PostDto createPost(PostRequest postRequest) {
    var post = postRepository.save(PostConverter.postRequestToPost(postRequest));
    return PostConverter.postToPostDto(post);
  }


  @Transactional
  public PostDto updatePost(Long id, PostRequest postRequest) {
    var post = postRepository.findById(id).orElseThrow(
        () -> new NoSuchPostException()
    );
    post.changeTitle(postRequest.getTitle());
    post.changeContent(postRequest.getContent());
    return PostConverter.postToPostDto(post);
  }
}
