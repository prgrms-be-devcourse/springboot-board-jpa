package org.prgrms.kdt.service;

import static java.text.MessageFormat.format;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.domain.Post;
import org.prgrms.kdt.domain.User;
import org.prgrms.kdt.dto.PostDto;
import org.prgrms.kdt.dto.PostDto.PostRequest;
import org.prgrms.kdt.mapper.PostMapper;
import org.prgrms.kdt.repository.PostRepository;
import org.prgrms.kdt.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

  private final PostMapper mapper;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Transactional
  public Long createPost(Long userId, PostRequest request) {
    User user = userRepository.getById(userId);
    Post post = mapper.of(request, user);

    return postRepository.save(post).getId();
  }

  @Transactional
  public Long updatePost(Long postId, PostRequest request) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException(
            format("ID: {0}의 게시글을 찾을 수 없습니다", postId)));
    post.changeTitle(request.title());
    post.changeContent(request.content());

    return post.getId();
  }

  public Page<PostDto.PostResponse> findPosts(Pageable pageable) {
    return postRepository.findAll(pageable).map(mapper::of);
  }

  public PostDto.PostResponse findPost(Long postId) {
    return postRepository.findById(postId)
        .map(mapper::of)
        .orElseThrow(() -> new EntityNotFoundException(
            format("ID {0}의 게시글을 찾을 수 없습니다", postId)));
  }

}