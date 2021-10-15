package spring.jpa.board.service;

import java.util.List;
import java.util.stream.Collectors;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.jpa.board.domain.Post;
import spring.jpa.board.domain.User;
import spring.jpa.board.dto.post.PostCreateRequest;
import spring.jpa.board.dto.post.PostFindRequest;
import spring.jpa.board.dto.post.PostModifyRequest;
import spring.jpa.board.repository.PostRepository;
import spring.jpa.board.repository.UserRepository;

@Slf4j
@Service
public class PostService {

  private final PostRepository postRepository;

  private final UserRepository userRepository;


  public PostService(PostRepository postRepository,
      UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public Long save(PostCreateRequest postCreateRequest) throws NotFoundException {
    User user = userRepository.findById(postCreateRequest.userId())
        .orElseThrow(() -> new NotFoundException("사용자 정보를 찾을 수 없습니다."));
    Post savePost = postRepository.save(
        new Post(
            postCreateRequest.title(),
            postCreateRequest.content(),
            user));
    if (savePost.getCreatedBy() == null) {
      savePost.setCreatedBy(user.getId());
      postRepository.save(savePost);
    }

    return savePost.getId();
  }

  @Transactional
  public Long update(Long id, PostModifyRequest postModifyRequest) throws NotFoundException {
    Post post = postRepository.findById(id).map(
        (entity) -> {
          Post savePost = new Post(
              entity.getId(),
              postModifyRequest.title(),
              postModifyRequest.content(),
              entity.getUser());
          savePost.setCreatedAt(entity.getCreatedAt());
          savePost.setCreatedBy(entity.getCreatedBy());
          return postRepository.save(savePost);
        }).orElseThrow(() -> new NotFoundException("게시글 정보를 찾을 수 없습니다."));
    return post.getId();
  }

  @Transactional
  public Page<PostFindRequest> findAll(Pageable pageable) {
    return postRepository.findAll(pageable)
        .map((item) -> new PostFindRequest(item.getId(), item.getTitle(), item.getContent(),
            item.getCreatedAt(), item.getCreatedBy(), item.getUser().getId()));
  }

  @Transactional
  public PostFindRequest findById(Long id) throws NotFoundException {
    return postRepository.findById(id)
        .map((item) -> new PostFindRequest(item.getId(), item.getTitle(), item.getContent(),
            item.getCreatedAt(), item.getCreatedBy(), item.getUser().getId()))
        .orElseThrow(() -> new NotFoundException("게시글 정보를 찾을 수 없습니다."));
  }

  @Transactional
  public List<PostFindRequest> findByUserId(Long id) {
    return postRepository.findByUserId(id)
        .stream()
        .map((item) -> new PostFindRequest(item.getId(), item.getTitle(), item.getContent(),
            item.getCreatedAt(), item.getCreatedBy(), item.getUser().getId()))
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteAll() {
    postRepository.deleteAll();
  }

  @Transactional
  public void deleteById(Long id) {
    postRepository.deleteById(id);
  }
}
