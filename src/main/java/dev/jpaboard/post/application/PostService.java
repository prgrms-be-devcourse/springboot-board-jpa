package dev.jpaboard.post.application;

import dev.jpaboard.post.domain.Post;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostUpdateRequest;
import dev.jpaboard.post.exception.PostNotFoundException;
import dev.jpaboard.post.repository.PostRepository;
import dev.jpaboard.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public PostResponse create(PostCreateRequest request) {
    Post post = PostCreateRequest.toPost(request);
    Post savedPost = postRepository.save(post);
    return PostResponse.toDto(savedPost);
  }

  @Transactional
  public PostResponse update(PostUpdateRequest request, Long postId) {
    Post post = postRepository.findById(postId)
            .orElseThrow(PostNotFoundException::new);
    post.update(request.title(), request.content());
    return PostResponse.toDto(post);
  }

  public PostResponse findPost(Long id) {
    Post post = postRepository.findById(id)
            .orElseThrow(PostNotFoundException::new);
    return PostResponse.toDto(post);
  }

  @Transactional
  public void delete(Long id) {
    postRepository.deleteById(id);
  }

  @Transactional
  public void deleteAll() {
    postRepository.deleteAll();
  }

}
