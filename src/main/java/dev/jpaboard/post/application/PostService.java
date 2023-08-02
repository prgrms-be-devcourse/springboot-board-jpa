package dev.jpaboard.post.application;

import dev.jpaboard.post.domain.Post;
import dev.jpaboard.post.dto.PostCreateRequest;
import dev.jpaboard.post.dto.PostResponse;
import dev.jpaboard.post.dto.PostUpdateRequest;
import dev.jpaboard.post.exception.PostNotFoundException;
import dev.jpaboard.post.repository.PostRepository;
import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.exception.UserNotFoundException;
import dev.jpaboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void update(Long postId, PostUpdateRequest request, Long userId) {
        Post post = findPostById(postId);
        User user = findUserById(userId);

        post.checkAuthorize(user);
        post.update(request.title(), request.content());
    }

    public PostResponse findPost(Long id) {
        Post post = findPostById(id);

        return PostResponse.from(post);
    }

    public PostsResponse findAll(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return PostsResponse.from(postPage);
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        Post post = findPostById(postId);
        User user = findUserById(userId);

        post.checkAuthorize(user);
        postRepository.deleteById(postId);
    }


}
